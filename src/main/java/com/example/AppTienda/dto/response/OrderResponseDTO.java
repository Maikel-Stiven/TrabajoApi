package com.example.AppTienda.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.AppTienda.enums.OrderStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class OrderResponseDTO {
    private Integer id;
    private UserResponseDTO client;
    private ProductResponseDTO product;
    private Integer quantity;
    private BigDecimal total;
    private OrderStatusEnum status;
    private LocalDateTime createdAt;
}
