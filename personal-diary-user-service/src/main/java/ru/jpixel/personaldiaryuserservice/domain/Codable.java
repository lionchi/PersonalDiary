package ru.jpixel.personaldiaryuserservice.domain;

/**
 * Интерфейс для перечислений с кодом.
 */
public interface Codable<S> {
    S getCode();

    /**
     * Выполняет поиск значения в перечислении по коду, если значение не найдено - то возвращает значение по умолчанию.
     *
     * @param codableClass класс - перечисление
     * @param code         код
     * @param defaultValue значение по умолчанию
     * @param <T>          перечисления
     * @return найденное значение перечилсения
     */
    static <T extends Enum<T> & Codable<S>, S> T find(Class<T> codableClass, S code, T defaultValue) {
        for (T codable : codableClass.getEnumConstants()) {
            if (codable.getCode().equals(code)) {
                return codable;
            }
        }
        return defaultValue;
    }
}
