package ru.jpixel.personaldiaryclient.web.security;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.jpixel.models.Error;
import ru.jpixel.models.OperationResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        if (e instanceof InsufficientAuthenticationException) {
            OperationResult operationResult = new OperationResult(Error.ACCESS_IS_DENIED);
            ObjectWriter objectWriter = new ObjectMapper().addMixIn(PrintWriter.class, JsonIgnoreType.class).writer();
            response.resetBuffer();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectWriter.writeValueAsString(operationResult));
            response.flushBuffer();
        }
    }
}
