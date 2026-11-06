package com.example.AppTienda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AppTienda.dto.HttpGlobalResponseDTO;
import com.example.AppTienda.dto.request.RegisterRequestDTO;
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
    public ResponseEntity<HttpGlobalResponseDTO<UserResponseDTO>> register(@Valid  @RequestBody RegisterRequestDTO request){
        UserResponseDTO user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(HttpGlobalResponseDTO.of("Usuario registrado exitosaente", user));
    }
}
