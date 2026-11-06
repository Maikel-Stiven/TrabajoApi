package com.example.AppTienda.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AppTienda.service.AuthService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/v1/tienda/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping ("/register")
    public ResponseEntity<HttpGlobalResponseDTO<UserResponseDTO>> register(@Valid  @RequestMapping )
}
