package ru.jpixel.personaldiaryservice.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.jpixel.models.dtos.common.SearchParams;
import ru.jpixel.personaldiaryservice.domain.open.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PageApplySearchParams extends ApplySearchParams<Page> {

    public Specification<Page> getSpecificationFilter(List<SearchParams.Filter> filters) {
        return getSpecificationFilter(filters, PageSpecificationFilter.class);
    }

    public Specification<Page> getSpecificationSort(Specification<Page> filteringSpec, List<SearchParams.OrderParameter> orderParameters) {
        if (CollectionUtils.isEmpty(orderParameters)) {
            return filteringSpec;
        }
        return getSpecificationSort(filteringSpec, orderParameters, PageSpecificationSort.class);
    }

    @Getter
    @RequiredArgsConstructor
    private enum PageSpecificationFilter implements Filtering<Page> {
        FIND_BY_DIARY_ID((value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join("diary").get("id"), value)),
        FIND_BY_NOTIFICATION_DATE((value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("notificationDate"), value)),
        FIND_BY_CREATE_DATE((value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), LocalDateTime.of((LocalDate) value, LocalTime.MIN)),
                criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), LocalDateTime.of((LocalDate) value, LocalTime.MAX))
        )),
        FIND_BY_CONFIDENTIAL((value) -> (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("confidential"), value)),
        FIND_BY_TAG((value) -> (root, criteriaQuery, criteriaBuilder) -> {
            String[] codes = String.valueOf(value).split(",");
            Set<String> codesSet = Stream.of(codes).collect(Collectors.toSet());
            return root.join("tag").get("code").in(codesSet);
        });

        private final SpecificationFilter<Page> specificationFilter;
    }

    @Getter
    @RequiredArgsConstructor
    private enum PageSpecificationSort implements Sorting<Page> {

        SORT_BY_NOTIFICATION_DATE(List.of(root -> root.get("notificationDate"))),
        SORT_BY_TAG(List.of(root -> root.join("tag").get("code"))),
        SORT_BY_CREATE_DATE(List.of(root -> root.get("createDate"))),
        SORT_BY_CONFIDENTIAL(List.of(root -> root.get("confidential")));

        private final List<SpecificationSort<Page>> specificationsSort;
    }
}
