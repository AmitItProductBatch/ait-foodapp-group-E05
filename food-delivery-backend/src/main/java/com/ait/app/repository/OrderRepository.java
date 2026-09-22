package com.ait.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.app.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>{

}
