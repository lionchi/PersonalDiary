package ru.jpixel.personaldiaryservice.services;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;
import ru.jpixel.models.dtos.common.SearchParams;
import ru.jpixel.personaldiaryservice.utils.SpecUtils;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ApplySearchParams<T> {

    /**
     * Применяет условия фильтрации
     *
     * @param filters     список фильтров с клиента
     * @param filterClass класс с перечнем доступных фильтров
     * @param <E>         тип класса с перечнем доступных фильтров
     * @return спецификация с условиями фильтрации
     */
    protected <E extends Enum<?> & Filtering<T>> Specification<T> getSpecificationFilter(List<SearchParams.Filter> filters, Class<E> filterClass) {
        var nameToValueMap = Arrays.stream(filterClass.getEnumConstants()).collect(Collectors.toMap(Enum::name, e -> e));

        var specsList = new ArrayList<Specification<T>>(filterClass.getEnumConstants().length);
        var alreadyProcessed = new HashSet<E>();

        for (var filter : filters) {
            var enumValue = nameToValueMap.get(filter.getNameFilter());

            if (enumValue == null) {
                continue;
            }

            Assert.state(!alreadyProcessed.contains(enumValue), String.format("Found more than one filter param with name %s", filter.getNameFilter()));
            alreadyProcessed.add(enumValue);

            specsList.add(enumValue.getSpecificationFilter().getSpecification(filter.getDataType().convert(filter.getValue())));
        }

        // Case 0, т.к. сортировочной спецификацией можно обернуть только не пустую спецификацию, то создаём фильтрующую
        // спецификацию, которая ничего не фильтрует
        return switch (specsList.size()) {
            case 0 -> (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(Boolean.TRUE));
            case 1 -> specsList.get(0);
            default -> SpecUtils.and(specsList);
        };
    }

    /**
     * Применяет условия сортировки
     *
     * @param filteringSpec   спецификация с условиями фильтрации
     * @param orderParameters список сортировок с клиента
     * @param sortClass       класс с перечнем доступных сортировок
     * @param <E>             тип класса с перечнем доступных сортировок
     * @return спецификация с условиями сортировки
     */
    protected <E extends Enum<?> & Sorting<T>> Specification<T> getSpecificationSort(Specification<T> filteringSpec,
                                                                                     List<SearchParams.OrderParameter> orderParameters,
                                                                                     Class<E> sortClass) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            var nameToValueMap = Arrays.stream(sortClass.getEnumConstants()).collect(Collectors.toMap(Enum::name, e -> e));
            var newOrderList = new ArrayList<>(criteriaQuery.getOrderList());

            for (var orderParameter : orderParameters) {
                E enumValue = nameToValueMap.get(orderParameter.getNameSort());

                if (enumValue == null) {
                    continue;
                }

                for (var tSpecificationSort : enumValue.getSpecificationsSort()) {
                    var sortPath = tSpecificationSort.getPath(root);
                    var order = Sort.Direction.fromString(orderParameter.getDirection()).isAscending()
                            ? criteriaBuilder.asc(sortPath)
                            : criteriaBuilder.desc(sortPath);
                    newOrderList.add(order);
                }
            }

            criteriaQuery.orderBy(newOrderList);
            return filteringSpec.toPredicate(root, criteriaQuery, criteriaBuilder);
        };
    }
}
