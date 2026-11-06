package com.example.AppTienda.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class UserCreatedRequestDTO {
    @NotBlank (message = "El username es obligatorio")
    private  String username;

    @NotBlank (message = "El email es obligatorio")
    @Email (message = "Debe ser un email valido")
    private String email;

    @NotBlank (message = "La contraseña es obligatoria")
    private String password;

    @NotNull (message = "El rolId es obligatorio")
    private Integer rolId;
}
