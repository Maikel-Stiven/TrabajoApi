package com.example.AppTienda.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@NoArgsConstructor
@AllArgsConstructor  
public class ProductResponseDTO {
    private  Integer id;
    private String name;
    private  String description;
    private  BigDecimal price;
    private Integer stock;
    private  CategoryResponseDTO category;
    private UserResponseDTO seller;
}
