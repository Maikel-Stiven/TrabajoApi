package com.example.AppTienda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 
public class LoginRequestDTO {
    @NotBlank (message = "El usuario o email es obligatorio")
    private String user;

    @NotBlank (message = "La contraseña es obligatoria")
    private String password;

}
