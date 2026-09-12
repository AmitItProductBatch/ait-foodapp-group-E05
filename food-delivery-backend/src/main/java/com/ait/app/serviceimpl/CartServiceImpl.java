package com.ait.app.serviceimpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.UserServiceCustomException;
import com.ait.app.model.Cart;
import com.ait.app.model.User;
import com.ait.app.repository.CartRepository;
import com.ait.app.repository.UserRepository;
import com.ait.app.requestbody.CartRequestDto;
import com.ait.app.requestbody.CartResponseDto;
import com.ait.app.service.CartService;

@Service
public class CartServiceImpl implements CartService{

	@Autowired	
	CartRepository cr;
	
	@Autowired
	UserRepository ur;
	
	@Override
	public CartResponseDto addCart(CartRequestDto dto) {
		// TODO Auto-generated method stub
		
		Optional<User> o = ur.findById(dto.getUserId());
        if (!o.isPresent()) {
            throw new UserServiceCustomException("User not found", HttpStatus.NOT_FOUND);
        }
		
		if (dto.getUserId() == null) {
            throw new UserServiceCustomException("UserId cannot be empty", HttpStatus.BAD_REQUEST);
        }
		
		if (cr.existsByUserId(dto.getUserId())) {
            throw new UserServiceCustomException("Cart already exists for this customer", HttpStatus.CONFLICT);
        }
	
		 Cart c = new Cart();
		 c.setUserId(dto.getUserId());
		 c.setRestaurantId(dto.getRestaurantId());
		 c.setCreatedAt(LocalDateTime.now());
		 c.setUpdatedAt(LocalDateTime.now()); 
		 Cart sc = cr.save(c);
		 
		 CartResponseDto cdto = new CartResponseDto();
		 cdto.setUserId(sc.getUserId());
		 cdto.setRestaurantId(sc.getRestaurantId());
		 cdto.setTotalAmount(sc.getTotalAmount());
		 cdto.setCreatedAt(cdto.getCreatedAt());
		 cdto.setUpdatedAt(sc.getUpdatedAt());
			
		return cdto;
	}

	@Override
	public CartResponseDto getCartByUserId(Long userId) {
		Optional<Cart> o = cr.findById(userId);
		if(!o.isPresent()) {
			
			throw new UserServiceCustomException("Cart not found for this user", HttpStatus.NOT_FOUND);
		}
	 
		Cart c = o.get();
		CartResponseDto dto = new CartResponseDto();
		dto.setId(c.getId());
		dto.setUserId(c.getUserId());
		dto.setRestaurantId(dto.getRestaurantId());
		dto.setTotalAmount(c.getTotalAmount());
		dto.setCreatedAt(c.getCreatedAt());
		dto.setUpdatedAt(dto.getUpdatedAt());
		return dto;
	}

	@Override
	public CartResponseDto updateCart(Long userId, CartRequestDto dto) {
		// TODO Auto-generated method stub
		Optional<Cart> o = cr.findById(userId);
        if (!o.isPresent()) {
            throw new UserServiceCustomException("Cart not found for this user", HttpStatus.NOT_FOUND);
        }
        Cart c = o.get();
        
        if(c.getUserId()!=null) {
        	c.setUserId(dto.getUserId());
        }
        if(c.getRestaurantId()!=null) {
        	c.setRestaurantId(dto.getRestaurantId());
        }
        
        Cart updated = cr.save(c);
        
        CartResponseDto rDto = new CartResponseDto();
        rDto.setId(updated.getId());
        rDto.setUserId(updated.getRestaurantId());
        rDto.setTotalAmount(updated.getTotalAmount());
        rDto.setCreatedAt(updated.getCreatedAt());
        rDto.setUpdatedAt(updated.getUpdatedAt());
		
		return rDto;
	}

	@Override
	public String deleteCart(Long userId) {
		// TODO Auto-generated method stub
		Optional<Cart> o = cr.findById(userId);
        if (!o.isPresent()) {
            throw new UserServiceCustomException("Cart not found for this user", HttpStatus.NOT_FOUND);
        }
        cr.delete(o.get());
        return "Cart cleared successfully";
	
	}
	

}