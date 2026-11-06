package com.example.AppTienda.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity 
@Table (name = "users")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class Users {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false, unique = true, length = 50)
    private String username;

    @Column (nullable = false, unique = true, length = 255)
    private String email;

    @Column (nullable = false, length = 255)
    private String password;

    @Column (nullable = false)
    @Builder.Default
    private Boolean active = true;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "rol_id", nullable = false)
    private Roles rol;
}
