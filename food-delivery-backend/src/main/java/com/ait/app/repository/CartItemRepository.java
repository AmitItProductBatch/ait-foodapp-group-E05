package com.ait.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.app.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	boolean existsByCartIdAndMenuItemId(Long cartId, int menuItemId);

	void deleteByCartId(Long cartId);
}
