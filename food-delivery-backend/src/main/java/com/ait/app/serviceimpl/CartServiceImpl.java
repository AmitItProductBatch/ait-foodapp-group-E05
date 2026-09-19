package com.ait.app.serviceimpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.UserServiceCustomException;
import com.ait.app.model.Cart;
import com.ait.app.model.User;
import com.ait.app.repository.CartItemRepository;
import com.ait.app.repository.CartRepository;
import com.ait.app.repository.UserRepository;
import com.ait.app.requestbody.CartRequestDto;
import com.ait.app.requestbody.CartResponseDto;
import com.ait.app.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartItemRepository cir;

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
		cdto.setId(sc.getId());
		cdto.setUserId(sc.getUserId());
		cdto.setRestaurantId(sc.getRestaurantId());
		cdto.setTotalAmount(sc.getTotalAmount());
		cdto.setCreatedAt(sc.getCreatedAt());
		cdto.setUpdatedAt(sc.getUpdatedAt());

		return cdto;
	}

	@Override
	public void clearCart(Long userId) {

		if (userId == null) {
			throw new UserServiceCustomException("userId cannot be empty", HttpStatus.BAD_GATEWAY);
		}

		Optional<Cart> optionalCart = cr.findByUserId(userId);

		if (!optionalCart.isPresent()) {
			throw new UserServiceCustomException("Cart not found for this user", HttpStatus.NOT_FOUND);
		}

		Cart cart = optionalCart.get();

		cir.deleteByCartId(cart.getId());

		cart.setRestaurantId(0);
		cart.setTotalAmount(0.0);
		cart.setUpdatedAt(LocalDateTime.now());

		cr.save(cart);

	}

}