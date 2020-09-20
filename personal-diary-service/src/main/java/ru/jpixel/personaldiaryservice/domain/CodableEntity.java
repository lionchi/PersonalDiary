package ru.jpixel.personaldiaryservice.domain;

import java.util.stream.Stream;

/**
 * Сущность, поддерживающая перечисление с кодом
 *
 * @param <T> тип кода в перечислении
 * @param <E> тип перечисления в сущности
 */
public interface CodableEntity<T, E extends Enum<E> & Codable<T>> {
    /**
     * Получить знаяение кода в виде перечисления
     */
    E getCodeEnum();

    /**
     * Получить значение перечисления по коду
     *
     * @param codableClass перечисление
     * @param code         искомый код
     * @return значение перечисления, соответсвующее коду
     */
    default E parseCode(Class<E> codableClass, T code) {
        return Codable.find(codableClass, code, null);
    }

    /**
     * Проверить, равно ли значение, возвращаемое {@code getCodeValue()}
     * одному из переданных в наборе {@code values}
     *
     * @param values набор возможных значений
     * @return true, елси соответсвует, иначе false
     */
    default boolean containsPredicate(E... values) {
        return Stream.of(values).anyMatch(v -> v == getCodeEnum());
    }
}
