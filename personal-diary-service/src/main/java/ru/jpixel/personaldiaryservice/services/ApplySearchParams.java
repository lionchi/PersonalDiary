package ru.jpixel.personaldiaryservice.services;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;
import ru.jpixel.models.dtos.common.SearchParams;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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
        Assert.notEmpty(filters, "Expected not empty list of filters");
        Assert.notNull(filterClass, "Expected not NULL filterClass");

        return (root, criteriaQuery, criteriaBuilder) -> {
            Specification<T> result = null;
            for (var filter : filters) {
                for (var enumConstant : filterClass.getEnumConstants()) {
                    if (filter.getNameFilter().equals(enumConstant.name())) {
                        var specificationOperation = enumConstant.getSpecificationFilter();
                        var tempSpecification = specificationOperation.getSpecification(filter.getValue());
                        if (result == null) {
                            result = tempSpecification;
                        } else {
                            result.and(tempSpecification);
                        }
                        break;
                    }
                }

            }

            Assert.notNull(result,
                    MessageFormat.format("Expected not NULL specification at the end of 'and'-joining. Source list of specifications contains '{0}'.", filters.size()));

            return result.toPredicate(root, criteriaQuery, criteriaBuilder);
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
            var newOrderList = new ArrayList<>(criteriaQuery.getOrderList());
            for (var orderParameter : orderParameters) {
                for (var enumConstant : sortClass.getEnumConstants()) {
                    if (orderParameter.getNameSort().equals(enumConstant.name())) {
                        for (var tSpecificationSort : enumConstant.getSpecificationsSort()) {
                            var sortPath = tSpecificationSort.getPath(root);
                            var order = Sort.Direction.fromString(orderParameter.getDirection()).isAscending()
                                    ? criteriaBuilder.asc(sortPath)
                                    : criteriaBuilder.desc(sortPath);
                            newOrderList.add(order);
                        }
                        break;
                    }
                }
            }
            criteriaQuery.orderBy(newOrderList);
            return filteringSpec.toPredicate(root, criteriaQuery, criteriaBuilder);
        };
    }
}
