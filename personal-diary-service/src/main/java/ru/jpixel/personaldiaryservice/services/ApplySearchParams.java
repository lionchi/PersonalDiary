package ru.jpixel.personaldiaryservice.services;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import ru.jpixel.models.dtos.common.SearchParams;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class ApplySearchParams<T> {
    public Sort getSort(List<SearchParams.OrderParameter> orderParameters) {
        var orders = new ArrayList<Sort.Order>();
        for (var orderParameter : orderParameters) {
            var direction = Sort.Direction.fromString(orderParameter.getDirection());
            orders.add(new Sort.Order(direction, orderParameter.getFieldName()));
        }
        return CollectionUtils.isEmpty(orders) ? Sort.unsorted() : Sort.by(orders);
    }

    protected <E extends Enum<?> & Operating<T>> Specification<T> getSpecification(List<SearchParams.Filter> filters, Class<E> operationClass) {
        Assert.notEmpty(filters, "Expected not empty list of filters");
        Assert.notNull(operationClass, "Expected not NULL operationClass");

        return (root, criteriaQuery, criteriaBuilder) -> {
            Specification<T> result = null;
            for (var filter : filters) {
                for (var enumConstant : operationClass.getEnumConstants()) {
                    if (filter.getNameOperation().equals(enumConstant.name())) {
                        var specificationOperation = enumConstant.getSpecificationOperation();
                        var tempSpecification = specificationOperation.getSpecification(filter.getFieldName(), filter.getValue());
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
}
