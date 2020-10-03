package ru.jpixel.models.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchParams {
    /**
     * Номер страницы
     */
    private int pageNumber;

    /**
     * Размер страницы
     */
    private int pageSize;

    /**
     * Дополнительные фильтры
     */
    private List<Filter> additionalFilter = new ArrayList<>();

    /**
     * Сортировка
     */
    private List<OrderParameter> orderParameters = new ArrayList<>();

    /**
     * Дополнительные фильтры
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Filter {
        /**
         * Наименование фильтра
         */
        private String nameFilter;

        /**
         * Значение
         */
        private Object value;
    }

    /**
     * Класс содержащий информацию о сортировке данных
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderParameter {
        /**
         * Наименование сортировки
         */
        private String nameSort;
        /**
         * Направление сортировки
         */
        private String direction;
    }
}
