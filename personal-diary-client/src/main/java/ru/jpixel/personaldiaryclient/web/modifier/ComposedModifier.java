package ru.jpixel.personaldiaryclient.web.modifier;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Применяет к шаблону список {@link TemplateModifier}.
 */
public class ComposedModifier implements TemplateModifier {

    private final List<TemplateModifier> modifiers;

    public ComposedModifier(TemplateModifier... modifiers) {
        this.modifiers = Arrays.asList(modifiers);
    }

    @Override
    public String modify(HttpServletRequest request, String template) {
        var current = template;
        for (var modifier : modifiers) {
            current = modifier.modify(request, current);
        }
        return current;
    }
}
