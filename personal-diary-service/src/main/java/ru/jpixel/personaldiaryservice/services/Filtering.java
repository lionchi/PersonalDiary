package ru.jpixel.personaldiaryservice.services;

@FunctionalInterface
public interface Filtering<E> {
    SpecificationFilter<E> getSpecificationFilter();
}
