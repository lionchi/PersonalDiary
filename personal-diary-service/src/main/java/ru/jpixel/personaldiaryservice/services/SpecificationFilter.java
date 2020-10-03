package ru.jpixel.personaldiaryservice.services;

import org.springframework.data.jpa.domain.Specification;

@FunctionalInterface
public interface SpecificationFilter<T> {
    Specification<T> getSpecification(Object value);
}
