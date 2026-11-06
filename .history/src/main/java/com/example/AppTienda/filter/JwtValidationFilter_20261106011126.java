package com.example.AppTienda.filter;

import com.example.AppTienda.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component 
public class JwtValidationFilter  extends OncePerRequestFilter{

    private final JwtService jwtService;

    public JwtValidationFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override 
    protected boolean shouldNotFIlter(HttpServletRequest request) throws ServletException{
        String path = request.getRequestURI();
        String method = request.getMethod();

        if (path.startsWith("/api/v1/tienda/auth")) {
            return true;
        }

        if ("GET".equalsIgnoreCase(method) && path.matches("^/api/v1/tienda/products(/\\d+)?$")) {
            return true;
        }

        return false;
    }

    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, io.jsonwebtoken.io.IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
          sendUnauthorizedResponse(response, "Token JWT ausente o formato inválido");
            return;
        }

        String token = authHeader.substring(7);

        try{
            Claims claims = jwtService.validateToken(token);
            request.setAttribute("username", claims.getSubject());
            request.setAttribute("userId", claims.get("userId", Integer.class));
            request.setAttribute("rolId", claims.get("rolId", Integer.class));
        }catch (Exception e){
            sendUnauthorizedResponse(response, "Token expirado o inválido: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        String json = String.format( "{\"message\":\"%s", " ,message)
    }
}
