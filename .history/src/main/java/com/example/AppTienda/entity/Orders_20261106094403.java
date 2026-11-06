package com.example.AppTienda.entity;

import com.example.AppTienda.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity 
@Table (name = "orders")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builderpublic OrdersBuilder client(Users client) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'client'");
    } 
public class Orders {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "product_id", nullable = false)
    private Products product;

    @Column (nullable = false)
    private Integer quantity;

    @Column (nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false, length = 20)
    private OrderStatusEnum status = OrderStatusEnum.PENDIENTE;

    @Column (name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist 
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
