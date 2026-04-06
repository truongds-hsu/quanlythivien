package com.library.config;

import com.library.model.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (uri.startsWith("/login") || uri.startsWith("/css") || uri.startsWith("/h2-console")) {
            return true;
        }
        Member currentUser = (Member) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
