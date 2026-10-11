package com.ait.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.app.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
