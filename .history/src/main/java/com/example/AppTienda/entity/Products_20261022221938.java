package com.example.AppTienda.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity 
@Table (name = "products")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 

public class Products {
    

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false, length = 150)
    private String name;

    @Column (length = 500)
    private String description;

    @Column (nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column (nullable = false)
    private Integer stock;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "category_id", nullable = false)
    private Categories category;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "seller_id", nullable = false)
    private Users seller;

}

