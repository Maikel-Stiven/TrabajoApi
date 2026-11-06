package com.example.AppTienda.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.AppTienda.dto.request.RegisterRequestDTO;
import com.example.AppTienda.dto.response.UserResponseDTO;
import com.example.AppTienda.exception.CustomException;
import com.example.AppTienda.repository.RolesRepository;
import com.example.AppTienda.repository.UserRepository;

@Service 
public class AuthService {
    
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private  final JwtService jwtService;

    public AuthService(
        UserRepository userRepository,
        RolesRepository rolesRepository,
        BCryptPasswordEncoder passwordEncoder,
        JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponseDTO register(RegisterRequestDTO dto){
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new CustomException("El nombre de usuario ya esta en uso", HttpStatus.CONFLICT);
            
        }
    }

}
