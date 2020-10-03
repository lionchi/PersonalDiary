package ru.jpixel.models.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
        private String value;

        /**
         * Тип значения
         */
        private DataType dataType;

        /**
         * Перечисление с типами данных
         */
        public enum DataType {
            @JsonProperty("Integer")
            INTEGER {
                public Integer convert(String value) {
                    return Integer.valueOf(value);
                }
            },
            @JsonProperty("Long")
            LONG {
                public Long convert(String value) {
                    return Long.valueOf(value);
                }
            },
            @JsonProperty("String")
            STRING {
                public String convert(String value) {
                    return value;
                }
            },
            @JsonProperty("Date")
            DATE {
                public LocalDate convert(String value) {
                    return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                }
            },
            @JsonProperty("BigDecimal")
            BIG_DECIMAL {
                public BigDecimal convert(String value) {
                    return new BigDecimal(value);
                }
            },
            @JsonProperty("Boolean")
            BOOLEAN {
                public Boolean convert(String value) {
                    return Boolean.valueOf(value);
                }
            };

            public static DataType parse(String value) {
                return Stream.of(values())
                        .filter(dataType -> dataType.name().equals(value))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Error parse {0} by code: {1}", DataType.class.getName(), value)));
            }

            public abstract Object convert(String value);
        }
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
