package com.ait.app.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.CartItemCustomException;
import com.ait.app.model.CartItem;
import com.ait.app.repository.CartItemRepository;
import com.ait.app.requestbody.OrderTotalRequestDto;
import com.ait.app.requestbody.OrderTotalResponseDto;
import com.ait.app.service.OrderTotalService;

@Service
public class OrderTotalServiceImpl implements OrderTotalService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public OrderTotalResponseDto calculateOrderTotal(OrderTotalRequestDto request) {

		// Get all items from cart
		List<CartItem> cartItems = cartItemRepository.findByCartId(request.getCartId());

		// Check empty cart
		if (cartItems.isEmpty()) {
			throw new CartItemCustomException("Cart is empty", HttpStatus.BAD_REQUEST);
		}

		double itemSubtotal = 0.0;

		for (CartItem cartItem : cartItems) {
			double subtotal = cartItem.getUnitPrice() * cartItem.getQuantity();

			itemSubtotal = itemSubtotal + subtotal;
		}

		double tax = itemSubtotal * 0.05;

		double deliveryFee = 40.0;

		double discount = 0.0;

		double total = itemSubtotal + tax + deliveryFee - discount;

		OrderTotalResponseDto response = new OrderTotalResponseDto();

		response.setItemSubtotal(itemSubtotal);
		response.setTax(tax);
		response.setDeliveryFee(deliveryFee);
		response.setDiscount(discount);
		response.setTotal(total);

		return response;
	}
}
