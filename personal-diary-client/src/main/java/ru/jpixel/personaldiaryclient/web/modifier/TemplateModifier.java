package ru.jpixel.personaldiaryclient.web.modifier;

import javax.servlet.http.HttpServletRequest;

/**
 * Модифицирует шаблон на основе данных {@link HttpServletRequest}.
 */
public interface TemplateModifier {

    /**
     * Модифицировать шаблон на основе данных реквеста.
     *
     * @param request  реквест
     * @param template шаблон
     * @return модифицированный шаблон
     */
    String modify(HttpServletRequest request, String template);
}
