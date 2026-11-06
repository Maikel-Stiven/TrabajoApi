package com.example.AppTienda.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data 
public class ProductRequestDTO {
    @NotBlank (message = "El nombre del producto es obligatorio")
    private String name;

    private String description;

    @NotNull (message = "El precio debe ser registrado")
    @Positive (message = "El precio debe ser un número positivo")
    private BigDecimal price;

    @NotNull (message = "El stock debe ser obligatorio")
    @Min (value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull (message = "El categoryId es obligatorio")
    private Integer categoryId;
}
