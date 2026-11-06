package com.example.AppTienda.repository;

import com.example.AppTienda.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface RolesRepository extends JpaRepository<Roles, Integer> {    
}
