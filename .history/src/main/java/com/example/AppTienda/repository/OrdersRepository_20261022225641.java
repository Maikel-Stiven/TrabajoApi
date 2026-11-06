package com.example.AppTienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.AppTienda.entity.Orders;

@Repository 
public interface OrdersRepository  extends JpaRepository<Orders, Integer>{
    List<Orders> findByClientId(Integer clientId);
    List<Orders> findByProductsSellerId(Integer sellerId);
}
