package com.example.AppTienda.repository;

import com.example.AppTienda.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface CategoryRepository  extends JpaRepository<Categories, Integer> {
    boolean existsByName(String name);
}
