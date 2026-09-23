package com.ait.app.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ait.app.exception.UserServiceCustomException;
import com.ait.app.model.Cart;
import com.ait.app.model.CartItem;
import com.ait.app.model.MenuItem;
import com.ait.app.model.User;
import com.ait.app.repository.CartRepository;
import com.ait.app.repository.MenuItemRepository;
import com.ait.app.repository.UserRepository;
import com.ait.app.requestbody.CartItemDetailsDto;
import com.ait.app.requestbody.CartRequestDto;
import com.ait.app.requestbody.CartResponseDto;
import com.ait.app.service.CartService;

@Service
public class CartServiceImpl implements CartService{

	@Autowired	
	CartRepository cr;
	
	@Autowired
	UserRepository ur;
	
	@Autowired
	MenuItemRepository mir;
	
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
		 cdto.setId(sc.getId());
		 cdto.setUserId(sc.getUserId());
		 cdto.setRestaurantId(sc.getRestaurantId());
		 cdto.setTotalAmount(sc.getTotalAmount());
		 cdto.setCreatedAt(sc.getCreatedAt());
		 cdto.setUpdatedAt(sc.getUpdatedAt());
			
		return cdto;
	}

	@Override
	public CartResponseDto getCart(Long userId) {
		// TODO Auto-generated method stub
		
        if (userId == null) {
            throw new UserServiceCustomException("UserId cannot be empty", HttpStatus.BAD_REQUEST);
        }
        
        Cart c = cr.findByUserId(userId);
        CartResponseDto responseDto = new CartResponseDto();
        List<CartItemDetailsDto> itemDetailsList = new ArrayList();
        
        if (c == null) {
            responseDto.setId(null);
            responseDto.setUserId(userId);
            responseDto.setRestaurantId(0);
            responseDto.setTotalAmount(0.0); 
            responseDto.setItems(itemDetailsList);
            return responseDto;
        }
        
        responseDto.setId(c.getId());
        responseDto.setUserId(c.getUserId());
        responseDto.setRestaurantId(c.getRestaurantId());
        
        if (c.getTotalAmount() != null) {
            responseDto.setTotalAmount(c.getTotalAmount());
        } else {
            responseDto.setTotalAmount(0.0);
        }
        
        responseDto.setCreatedAt(c.getCreatedAt());
        responseDto.setUpdatedAt(c.getUpdatedAt());
        
        if (c.getCartItems() != null && !c.getCartItems().isEmpty()) {
            for (CartItem item : c.getCartItems()) {
                CartItemDetailsDto itemDto = new CartItemDetailsDto();
                itemDto.setId(item.getId());
                itemDto.setMenuItemId(item.getMenuItemId());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setUnitPrice(item.getUnitPrice());
                itemDto.setSubtotal(item.getSubtotal());
                
                Optional<MenuItem> o = mir.findById(item.getMenuItemId());
                if (o.isPresent()) {
                    itemDto.setName(o.get().getName());
                } else {
                    itemDto.setName("Unknown Selection");
                }

                itemDetailsList.add(itemDto);
            }
        }

        responseDto.setItems(itemDetailsList);
        return responseDto;
    }

	@Override
	@Transactional
	public void clearCart(Long userId) {

		
		if (userId == null) {
			throw new UserServiceCustomException(
					"UserId cannot be empty",
					HttpStatus.BAD_REQUEST);
		}

		Optional<User> user = ur.findById(userId);

		if (!user.isPresent()) {
			throw new UserServiceCustomException(
					"User not found",
					HttpStatus.NOT_FOUND);
		}


		Cart c = cr.findByUserId(userId);

		if (c == null) {
			return;
		}

		if (c.getUserId() == null || !c.getUserId().equals(userId)) {
			throw new UserServiceCustomException(
					"You are not allowed to clear this cart",
					HttpStatus.FORBIDDEN);
		}

		if (c.getCartItems() != null) {
			c.getCartItems().clear();
		}

		
		c.setRestaurantId(0);

		
		c.setTotalAmount(0.0);

		
		c.setUpdatedAt(LocalDateTime.now());

		
		cr.save(c);
	}
                

	

}