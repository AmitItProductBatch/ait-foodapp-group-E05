package com.ait.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ait.app.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	boolean existsByUserId(Long userId);
	
    Cart findByUserId(Long userId); 

	Optional<Cart> findByUserId(Long userId);
}