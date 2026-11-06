package com.example.AppTienda.repository;

import com.example.AppTienda.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByClientId(Integer clientId);
    List<Orders> findByProductSellerId(Integer sellerId);
}