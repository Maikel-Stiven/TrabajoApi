package com.example.AppTienda.service;

import com.example.AppTienda.dto.request.UserCreatedRequestDTO;
import com.example.AppTienda.dto.response.UserResponseDTO;
import com.example.AppTienda.entity.Roles;
import com.example.AppTienda.entity.Users;
import com.example.AppTienda.exception.CustomException;
import com.example.AppTienda.repository.RolesRepository;
import com.example.AppTienda.repository.UserRepository;
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
        UsersRepository usersRepository,
        
    )
}
