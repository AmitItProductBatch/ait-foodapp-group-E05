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
import com.ait.app.requestbody.CartResponseDto;
import com.ait.app.requestbody.QuantityUpdateDTO;
import com.ait.app.service.CartItemService;

import jakarta.transaction.Transactional;

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
	@Transactional
	public CartResponseDto updateQuantity(Long itemId, QuantityUpdateDTO dto) {
		if(dto==null) {
			throw new CartItemCustomException("request body can not be empty", HttpStatus.BAD_REQUEST);
	
	}
       if(dto.getQuantity()<0) {
    	   throw new CartItemCustomException("Quantity cannot be negative", HttpStatus.BAD_REQUEST);
       }
Optional<CartItem> optionalCartItem=cir.findById(itemId);
if(!optionalCartItem.isPresent()) {
	throw new CartItemCustomException("CartItem not found in users cart", HttpStatus.NOT_FOUND);
	
}
CartItem cartitem=optionalCartItem.get();
Cart cart=cartitem.getCart();
if(cart==null) {
	throw new CartItemCustomException("Cart not found", HttpStatus.NOT_FOUND);
}
if(dto.getQuantity()==0) {
	cart.getCartItems().remove(cartitem);
	cir.delete(cartitem);
}else {
	cartitem.setQuantity(dto.getQuantity());
	double subtotal=dto.getQuantity()*cartitem.getUnitPrice();
	cartitem.setSubtotal(subtotal);
	cir.save(cartitem);
}
double totalAmount=0.0;
for(CartItem item:cart.getCartItems()) {
	totalAmount+=item.getSubtotal();
}
cart.setTotalAmount(totalAmount);
cart.setUpdatedAt(LocalDateTime.now());
cr.save(cart);

CartResponseDto response = new CartResponseDto();
response.setId(cart.getId());
response.setUserId(cart.getUserId());
response.setRestaurantId(cart.getRestaurantId());
response.setTotalAmount(cart.getTotalAmount());
response.setCreatedAt(cart.getCreatedAt());
response.setUpdatedAt(cart.getUpdatedAt());
       return response ;
	}
	
}
