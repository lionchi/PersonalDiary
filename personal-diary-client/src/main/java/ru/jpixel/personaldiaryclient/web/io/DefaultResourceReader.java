package ru.jpixel.personaldiaryclient.web.io;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Реализация {@link ResourceReader} по умолчанию.
 */
@Component
public class DefaultResourceReader implements ResourceReader {
    @Override
    public String read(String path) {
        var resource = new ClassPathResource(path);
        try (var result = new ByteArrayOutputStream(); var resourceInputStream = resource.getInputStream()) {
            var buffer = new byte[1024];
            int length;
            while ((length = resourceInputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Can't read " + path, e);
        }
    }
}
