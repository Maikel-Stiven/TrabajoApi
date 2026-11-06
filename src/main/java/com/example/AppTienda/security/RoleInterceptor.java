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
            annotation = handlerMethod.getBeanType().getAnnotation(RequiresRole.class);
        }

        if (annotation == null) {
            return true;
        }

        Integer userRolId = (Integer) request.getAttribute("rolId");
        if (userRolId == null) {
            throw new CustomException("No autenticado", HttpStatus.UNAUTHORIZED);
        }

        RoleEnum userRole = RoleEnum.fromId(userRolId);
        boolean hasPermission = Arrays.asList(annotation.value()).contains(userRole);

        if (!hasPermission) {
            throw new CustomException("Acesso denegado: no tienes permisos suficientes", HttpStatus.FORBIDDEN);      
        }
        return true;
        
    }
}
