package ru.jpixel.personaldiaryclient.web.modifier;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Выполняет замену {@code /${baseUrl}} на значение {@link HttpServletRequest#getContextPath()}
 */
public class BaseUrlModifier implements TemplateModifier {

    private final Pattern PATTERN = Pattern.compile("<head>");

    @Override
    public String modify(HttpServletRequest request, String template) {
        return PATTERN.matcher(template).replaceFirst(urlHeader(request));
    }

    private String urlHeader(HttpServletRequest request) {
        return appUrl(request) + baseUrl(request);
    }

    private String appUrl(HttpServletRequest request) {
        return "<head><meta name=\"application-url\" content=\"" + request.getContextPath() + "\">";
    }

    private String baseUrl(HttpServletRequest request) {
        var contextPath = request.getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        return "<base href=\"" + contextPath + "\">";
    }

}
