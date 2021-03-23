package ru.jpixel.personaldiaryservice.utils;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.List;

public final class SpecUtils {

    private SpecUtils() {
    }

    public static <T> Specification<T> and(List<Specification<T>> specifications) {
        Assert.notEmpty(specifications, "Expected not empty list of specifications.");

        return (root, query, criteriaBuilder) -> {
            Specification<T> result = null;

            for (Specification<T> specification : specifications) {
                if (specification == null) {
                    continue;
                }

                if (result == null) {
                    result = specification;
                } else {
                    result = result.and(specification);
                }
            }

            Assert.notNull(
                    result,
                    MessageFormat.format(
                            "Expected not NULL specification at the end of 'and'-joining. Source list of specifications contains ''{0}''.",
                            specifications.size()
                    )
            );

            return result.toPredicate(root, query, criteriaBuilder);
        };
    }

}
