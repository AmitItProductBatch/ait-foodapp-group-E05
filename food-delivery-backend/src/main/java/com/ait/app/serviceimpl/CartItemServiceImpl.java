package com.ait.app.serviceimpl;

import java.time.LocalDateTime;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.CartItemCustomException;
import com.ait.app.model.Cart;
import com.ait.app.model.CartItem;
import com.ait.app.model.MenuItem;
import com.ait.app.repository.CartItemRepository;
import com.ait.app.repository.CartRepository;
import com.ait.app.repository.MenuItemRepository;
import com.ait.app.requestbody.CartItemRequestDto;
import com.ait.app.requestbody.CartItemResponseDto;
import com.ait.app.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	CartItemRepository cir;
	
	@Autowired
	CartRepository cr;
	
	@Autowired
	MenuItemRepository mir;

	@Override
	public CartItemResponseDto addToCart(CartItemRequestDto dto) {
		// TODO Auto-generated method stub
		
		 if (dto.getCartId() == null ) {
	            throw new CartItemCustomException("Missing mandatory fields: cartId and menuItemId", HttpStatus.BAD_REQUEST);
	        }
		 
		 if ( dto.getQuantity() < 1) {
	            throw new CartItemCustomException("Quantity must be a positive", HttpStatus.BAD_REQUEST);
	        }
		 
		 
		 Optional<Cart> o = cr.findById(dto.getCartId());
		 
		 if(!o.isPresent()) {
			 throw new CartItemCustomException("Cart container not found", HttpStatus.NOT_FOUND);
		 }
		 Cart c = o.get();
		 
		 Optional<MenuItem> om = mir.findById(dto.getMenuItemId());
		 if(!om.isPresent()) {
			 throw new CartItemCustomException("Menu item not found", HttpStatus.NOT_FOUND);
		 }
		 MenuItem mi = om.get();
		 
		 if (cir.existsByCartIdAndMenuItemId(dto.getCartId(), dto.getMenuItemId())) {
	            throw new CartItemCustomException("Item already exists inside this cart", HttpStatus.CONFLICT);
	        }
		 
		 
		 
		 CartItem ci = new CartItem();
		 ci.setCart(c);
		 ci.setMenuItemId(dto.getMenuItemId());
		 ci.setQuantity(dto.getQuantity());
		 ci.setSubtotal(dto.getQuantity() * mi.getPrice());
		 ci.setUnitPrice(mi.getPrice());
		 CartItem savedItem= cir.save(ci);
		 
		 c.setTotalAmount(c.getTotalAmount() + savedItem.getSubtotal());
		 c.setUpdatedAt(LocalDateTime.now());
		 cr.save(c);
		 
		 CartItemResponseDto response = new CartItemResponseDto();
		 response.setId(savedItem.getId());
		 response.setCartId(savedItem.getCart().getId());
		 response.setMenuItemId(savedItem.getMenuItemId());
		 response.setQuantity(savedItem.getQuantity());
		 response.setSubtotal(savedItem.getSubtotal());
		 response.setUnitPrice(savedItem.getUnitPrice());
		 
		return response;
	}

	@Override
	public void deleteCartItem(int itemId) {
		Optional<CartItem> optionalCartItem = cir.findById(itemId);

	    if (!optionalCartItem.isPresent()) {
	        throw new CartItemCustomException(
	                "Cart item not found",
	                HttpStatus.NOT_FOUND
	        );
	    }

	    CartItem cartItem = optionalCartItem.get();
	    Cart cart = cartItem.getCart();

	    cir.delete(cartItem);

	    double remainingTotal = cart.getTotalAmount() - cartItem.getSubtotal();

	    if (remainingTotal < 0) {
	        remainingTotal = 0;
	    }

	    cart.setTotalAmount(remainingTotal);
	    cart.setUpdatedAt(LocalDateTime.now());

	    cr.save(cart);
	}
	
}

