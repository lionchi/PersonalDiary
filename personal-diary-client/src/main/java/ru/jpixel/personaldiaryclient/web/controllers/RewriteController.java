package ru.jpixel.personaldiaryclient.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jpixel.personaldiaryclient.web.io.ResourceReader;
import ru.jpixel.personaldiaryclient.web.modifier.BaseUrlModifier;
import ru.jpixel.personaldiaryclient.web.modifier.ComposedModifier;
import ru.jpixel.personaldiaryclient.web.modifier.TemplateModifier;

import javax.servlet.http.HttpServletRequest;

/**
 * Модифицирует шаблон страницы
 */
@RestController
public class RewriteController {

    private final TemplateModifier modifier;
    private final String template;

    public RewriteController(ResourceReader reader) {
        modifier = new ComposedModifier(new BaseUrlModifier());
        template = reader.read("/public/index.html");
    }

    @GetMapping("/index.html")
    public String index(HttpServletRequest request) {
        return modifier.modify(request, template);
    }
}
