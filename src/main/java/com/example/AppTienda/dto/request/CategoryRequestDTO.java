package com.example.AppTienda.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 
public class CategoryRequestDTO {
    @NotBlank (message = "El nombre de la categoría es obligatorio")
    private String name;
}
