package com.example.AppTienda.repository;


import com.example.AppTienda.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository 
public interface UserRepository extends JpaRepository<Users, Integer>{
    Optional<Users> findByUsernameOrEmail(String username, String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
