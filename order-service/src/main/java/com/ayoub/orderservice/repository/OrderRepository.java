package com.ayoub.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayoub.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

    
}
