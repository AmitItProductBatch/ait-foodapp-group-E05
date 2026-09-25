package com.ait.app.repository;

import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ait.app.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	boolean existsByCartIdAndMenuItemId(Long cartId, int menuItemId);

	void deleteByCartId(Long cartId);
	
	Optional<CartItem> findByIdAndCartId(Long itemId, Long cartId);
	
	Optional<CartItem> findById(int id);
	
	List<CartItem> findByCartId(Long cartId);
}
