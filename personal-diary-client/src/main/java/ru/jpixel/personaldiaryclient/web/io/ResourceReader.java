package ru.jpixel.personaldiaryclient.web.io;

/**
 * Читает ресурс из {@code classpath}.
 */
public interface ResourceReader {

    /**
     * Вернуть содержимое ресурса в виде строки в UTF-8.
     *
     * @param path путь к ресурсу
     * @return ресурс в виде строки
     */
    String read(String path);
}
