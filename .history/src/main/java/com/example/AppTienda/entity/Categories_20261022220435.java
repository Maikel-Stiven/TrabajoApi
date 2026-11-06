package com.example.AppTienda.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table (name = "categories")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class Categories {
    

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false, unique = true, length = 100)
    private String name;
}
