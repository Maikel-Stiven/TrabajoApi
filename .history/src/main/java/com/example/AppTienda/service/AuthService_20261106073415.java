package com.example.AppTienda.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.AppTienda.dto.request.LoginRequestDTO;
import com.example.AppTienda.dto.request.RegisterRequestDTO;
import com.example.AppTienda.dto.response.AuthResponseDTO;
import com.example.AppTienda.dto.response.UserResponseDTO;
import com.example.AppTienda.entity.Roles;
import com.example.AppTienda.entity.Users;
import com.example.AppTienda.enums.RoleEnum;
import com.example.AppTienda.exception.CustomException;
import com.example.AppTienda.repository.RolesRepository;
import com.example.AppTienda.repository.UserRepository;

import io.jsonwebtoken.Claims;

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

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException("El correo ya se encuentra registrado", HttpStatus.CONFLICT);
        }

        Roles clientRole = rolesRepository.findById(RoleEnum.CLIENTE.getId())
        .orElseThrow(() -> new CustomException("Rol CLIENTE no encontrado en el sistema", HttpStatus.INTERNAL_SERVER_ERROR));

        Users newUser = Users.builder()
        .username(dto.getUsername())
        .email(dto.getEmail())
        .password(passwordEncoder.encode(dto.getPassword()))
        .active(true)
        .rol(clientRole)
        .build();


        Users savedUser = userRepository.save(newUser);
        return mapToUserResponse(savedUser);
    }

    public AuthResponseDTO login (LoginRequestDTO dto){
        Users user = userRepository.findByUsernameOrEmail(dto.getUser(), dto.getUser())
        .orElseThrow(() -> new CustomException("Credenciales invalida", HttpStatus.UNAUTHORIZED));

        if (!user.getActive()) {
            throw new CustomException("El usuario se encuentra inactivo ", HttpStatus.UNAUTHORIZED);
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new  CustomException("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtService.generateToken(user.getUsername(), user.getId(), user.getRol().getId())
    
        return AuthResponseDTO.builder()
        .token(token)
        .user(mapToUserResponse(user))
        .build();
    }

    public AuthResponseDTO refreshToken (String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer")){
            throw new CustomException("Header de autorización invalido", HttpStatus.UNAUTHORIZED);
        }

        String oldToken = authHeader.substring(7);
        Claims claims = jwtService.validateToken(oldToken);

        String username = claims.getSubject();
        Integer userId = claims.get("userId", Integer.class);
        Integer rolId = claims.get("rolId", Integer.class);

        Users user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException("Usuario no encontrado", HttpStatus.NOT_FOUND));
    }

}
