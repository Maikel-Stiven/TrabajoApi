package com.example.AppTienda.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table (name = "roles")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 

public class Roles {
    
    
    @Id 
    @GeneratedValue (strategy =  GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false, length = 50)
    private String name;
}
