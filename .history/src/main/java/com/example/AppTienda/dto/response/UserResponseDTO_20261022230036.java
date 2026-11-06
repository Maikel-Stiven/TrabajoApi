package com.example.AppTienda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class UserResponseDTO {
    private Integer id;
    private String username;
    private String email;
    private Boolean active;
    private String role;
}
