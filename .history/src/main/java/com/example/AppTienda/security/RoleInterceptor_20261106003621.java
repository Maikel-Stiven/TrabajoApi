package com.example.AppTienda.security;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class RoleInterceptor implements  HandlerInterceptor{
    
    @Override 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
    }
}
