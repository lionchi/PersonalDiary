package ru.jpixel.personaldiaryservice.services;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

@FunctionalInterface
public interface SpecificationSort<T> {
    Expression<?> getPath(Root<T> root);
}
