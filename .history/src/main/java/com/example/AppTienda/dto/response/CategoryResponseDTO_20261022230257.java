package com.example.AppTienda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@NoArgsConstructor 
@AllArgsConstructor  
public class CategoryResponseDTO {
    private Integer id;
    private String name;
}
