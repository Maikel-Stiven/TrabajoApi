package com.example.AppTienda.service;

import com.example.AppTienda.dto.request.UserCreateRequestDTO;
import com.example.AppTienda.dto.request.UserCreatedRequestDTO;
import com.example.AppTienda.dto.response.UserResponseDTO;
import com.example.AppTienda.entity.Roles;
import com.example.AppTienda.entity.Users;
import com.example.AppTienda.exception.CustomException;
import com.example.AppTienda.repository.RolesRepository;
import com.example.AppTienda.repository.UserRepository;
import com.example.AppTienda.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service 
public class UsersService {
    
    private  final UserRepository userRepository;
    private  final RolesRepository rolesRepository;
    private  final BCryptPasswordEncoder passwordEncoder;

    public  UsersService(
        UserRepository userRepository,
        RolesRepository rolesRepository,
        BCryptPasswordEncoder passwordEncoder
    ){
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public  UserResponseDTO getProfile(Integer userId){
        Users user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException("Usuario no encontrado", HttpStatus.NOT_FOUND));
        return  mapToUserResponse(user);
    }

    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll().stream()
        .map(this::maptoUserResponse)
        .collect(Collectors.toList());
    }

    public UserResponseDTO createdUser(UserCreatedRequestDTO dto){
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new CustomException("El username ya existe", HttpStatus.CONFLICT);
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException("El correo ya existe", HttpStatus.CONFLICT);
        }

        Roles role = rolesRepository.findById(dto.getRolId())
        .orElseThrow(() -> new CustomException("Rol no valido", HttpStatus.BAD_REQUEST));

        Users user = Users.builder()
        .username(dto.getUsername())
        .email(dto.getEmail())
        .password(passwordEncoder.encode(dto.getPassword()))
        .active(true)
        .rol(role)
        .build();

        return mapToUserResponse(userRepository.save(user));
    }

    public  UserResponseDTO updateRole(Integer userId, Integer newRolId){
        Users user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException("Usuario no encontrado", HttpStatus.NOT_FOUND));
        Roles role = rolesRepository.findById(newRolId)
        .orElseThrow(() -> new CustomException("Rol especificado no existe", HttpStatus.BAD_REQUEST));
    
        user.setRol(role);
        return  mapToResponse(userRepository.save(user));
    }

    public UserResponseDTO updateStatus(Integer userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        user.setActive(!user.getActive());
        return mapToUserResponse(userRepository.save(user));
    }

    public UserResponseDTO mapToUserResponse(Users user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .active(user.getActive())
                .role(user.getRol().getName())
                .build();
    }
}
    }
}
