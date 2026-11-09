package com.example.AppTienda.controller;

import com.example.AppTienda.dto.HttpGlobalResponseDTO;
import com.example.AppTienda.dto.request.UserCreatedRequestDTO;
import com.example.AppTienda.dto.response.UserResponseDTO;
import com.example.AppTienda.enums.RoleEnum;
import com.example.AppTienda.security.RequiresRole;
import com.example.AppTienda.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/me")
    @RequiresRole({RoleEnum.ADMIN, RoleEnum.CLIENTE, RoleEnum.VENDEDOR})
    public ResponseEntity<HttpGlobalResponseDTO<UserResponseDTO>> getMyProfile(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        UserResponseDTO profile = usersService.getProfile(userId);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Perfil obtenido exitosamente", profile));
    }

    @GetMapping
    @RequiresRole(RoleEnum.ADMIN)
    public ResponseEntity<HttpGlobalResponseDTO<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = usersService.getAllUsers();
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Listado de usuarios obtenido", users));
    }

    @PostMapping
    @RequiresRole(RoleEnum.ADMIN)
    public ResponseEntity<HttpGlobalResponseDTO<UserResponseDTO>> createUser(@Valid @RequestBody UserCreatedRequestDTO request) {
        UserResponseDTO user = usersService.createdUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(HttpGlobalResponseDTO.of("Usuario creado exitosamente", user));
    }

    @PatchMapping("/{id}/role")
    @RequiresRole(RoleEnum.ADMIN)
    public ResponseEntity<HttpGlobalResponseDTO<UserResponseDTO>> changeRole(
            @PathVariable Integer id,
            @RequestParam Integer rolId) {
        UserResponseDTO user = usersService.updateRole(id, rolId);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Rol actualizado exitosamente", user));
    }

    @PatchMapping("/{id}/status")
    @RequiresRole(RoleEnum.ADMIN)
    public ResponseEntity<HttpGlobalResponseDTO<UserResponseDTO>> toggleStatus(@PathVariable Integer id) {
        UserResponseDTO user = usersService.updateStatus(id);
        return ResponseEntity.ok(HttpGlobalResponseDTO.of("Estado de usuario actualizado", user));
    }
}