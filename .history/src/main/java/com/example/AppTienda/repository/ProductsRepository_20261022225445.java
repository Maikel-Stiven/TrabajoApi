package com.example.AppTienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AppTienda.entity.Products;

@Repository 
public interface ProductsRepository extends JpaRepository<Products, Integer>{
    List<Products> findByCategoryId(Integer categoryId);
    List<Products> findBySellerId(Integer sellerId);
    boolean existsByCategoryId(Integer categoryId);
}
