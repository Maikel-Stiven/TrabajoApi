package com.example.AppTienda.controller;

import com.example.AppTienda.dto.HttpGlobalResponseDTO;
import com.example.AppTienda.dto.request.LoginRequestDTO;
import com.example.AppTienda.dto.request.RegisterRequestDTO;
import com.example.AppTienda.dto.response.AuthResponseDTO;
import com.example.AppTienda.dto.response.UserResponseDTO;
import com.example.AppTienda.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping ("/login")
    public ResponseEntity<HttpGlobalResponseDTO<AuthResponseDTO>> login(@Valid  @RequestBody LoginRequestDTO request){
        AuthResponseDTO authData = authService.login(request);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Autenticación exitosa", authData));
    }

    @PostMapping ("/refreshToken")
    public ResponseEntity<HttpGlobalResponseDTO<AuthResponseDTO>> refreshToken(@RequestHeader ("Authorization") String authHeader){
        AuthResponseDTO authData = authService.refreshToken(authHeader);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Token renovado", authData));
    }
}
