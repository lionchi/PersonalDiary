package ru.jpixel.personaldiaryservice.services;

import org.springframework.data.jpa.domain.Specification;

@FunctionalInterface
public interface SpecificationOperation<T> {
    Specification<T> getSpecification(String propertyName, Object value);
}
