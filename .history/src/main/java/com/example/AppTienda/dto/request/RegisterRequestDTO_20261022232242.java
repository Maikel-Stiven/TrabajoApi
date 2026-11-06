package com.example.AppTienda.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class RegisterRequestDTO {
    @NotBlank (message = "El username es obligatorio")
    @Size (min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres") 
}
