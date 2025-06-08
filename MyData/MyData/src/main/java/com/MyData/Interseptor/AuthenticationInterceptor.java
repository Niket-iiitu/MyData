package com.MyData.Interceptor;

import com.MyData.Dto.AuthSession;
import com.MyData.Service.AuthenticationService;
import com.MyData.Service.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LoggingService loggingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("uid");
        String sessionId = request.getHeader("session-id");

        if (userId == null || sessionId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing authentication headers.");
            return false;
        }

        AuthSession authSession = authenticationService.validateAndUpdateSession(userId, sessionId);
        if (Objects.equals(authSession.getStatus(), "FAILED") || Objects.equals(authSession.getStatus(), "TIMEOUT")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Session expired or invalid.");
            loggingService.logRequest("{}", "Session validation failed", request.getRequestURI(), userId, "ERROR");
            return false;
        }

        // Optionally attach the userId to request attributes for use in controllers
        request.setAttribute("userId", userId);

        return true;
    }
}
