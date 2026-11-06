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
        
    }
}
