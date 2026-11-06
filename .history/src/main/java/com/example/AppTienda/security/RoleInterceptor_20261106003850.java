package com.example.AppTienda.security;

import com.example.AppTienda.enums.RoleEnum;
import com.example.AppTienda.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component 
public class RoleInterceptor implements  HandlerInterceptor{
    
    @Override 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        RequiresRole annotation = handlerMethod.getMethodAnnotation(RequiresRole.class);
        
        if (annotation == null) {
            throw new CustomException("No autenticado", HttpStatus.UNAUTHORIZED);
        }
    }
}
