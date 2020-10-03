package ru.jpixel.personaldiaryservice.services;

import java.util.List;

@FunctionalInterface
public interface Sorting<T> {
    List<SpecificationSort<T>> getSpecificationsSort();
}
