package ru.jpixel.personaldiaryservice.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.jpixel.models.dtos.common.SearchParams;
import ru.jpixel.personaldiaryservice.domain.open.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PageApplySearchParams extends ApplySearchParams<Page> {

    public Specification<Page> getSpecification(List<SearchParams.Filter> filters) {
        return getSpecification(filters, PageSpecificationOperationEnum.class);
    }

    @Getter
    @RequiredArgsConstructor
    private enum PageSpecificationOperationEnum implements Operating<Page> {

        FIND_BY_DIARY_ID((propertyName, value) -> (root, criteriaQuery, criteriaBuilder) -> {
            var propertyNames = propertyName.split("\\.");
            return criteriaBuilder.equal(root.join(propertyNames[0]).get(propertyNames[1]), value);
        }),
        FIND_BY_NOTIFICATION_DATE((propertyName, value) -> (root, criteriaQuery, criteriaBuilder) -> {
            var searchNotificationDate = LocalDate.parse((String) value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return criteriaBuilder.equal(root.get(propertyName), searchNotificationDate);
        }),
        FIND_BY_CREATE_DATE((propertyName, value) -> (root, criteriaQuery, criteriaBuilder) -> {
            var searchCreateDate = LocalDate.parse((String) value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(root.get(propertyName), LocalDateTime.of(searchCreateDate, LocalTime.MIN)),
                    criteriaBuilder.lessThanOrEqualTo(root.get(propertyName), LocalDateTime.of(searchCreateDate, LocalTime.MAX))
            );
        }),
        FIND_BY_CONFIDENTIAL((propertyName, value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(propertyName), value)),
        FIND_BY_TAG((propertyName, value) -> (root, criteriaQuery, criteriaBuilder) -> {
            var propertyNames = propertyName.split("\\.");
            return criteriaBuilder.equal(root.join(propertyNames[0]).get(propertyNames[1]), value);
        });

        private final SpecificationOperation<Page> specificationOperation;
    }
}
