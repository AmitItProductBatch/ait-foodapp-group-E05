package com.ait.app.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.OrderServiceCustomException;
import com.ait.app.model.Cart;
import com.ait.app.model.CartItem;
import com.ait.app.model.MenuItem;
import com.ait.app.model.OrderItem;
import com.ait.app.model.Orders;
import com.ait.app.repository.CartItemRepository;
import com.ait.app.repository.CartRepository;
import com.ait.app.repository.MenuItemRepository;
import com.ait.app.repository.OrderRepository;
import com.ait.app.requestbody.OrderRequestDto;
import com.ait.app.requestbody.OrderResponseDto;
import com.ait.app.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderRepository or;
	
	@Autowired
	CartRepository cr;
	
	@Autowired
	MenuItemRepository mir;
	
	@Autowired
	CartItemRepository cir;

	@Override
	public OrderResponseDto placeOrder(OrderRequestDto dto) {
		// TODO Auto-generated method stub
		Cart c = cr.findByUserId(dto.getUserId());
		
        if (c == null || c.getCartItems() == null || c.getCartItems().isEmpty() || c.getTotalAmount() == null || c.getTotalAmount() <= 0.0) {
            throw new OrderServiceCustomException("Cannot place order: Cart is empty or has zero balance", HttpStatus.BAD_REQUEST);
        }
        
        for (CartItem item : c.getCartItems()) {
            Optional<MenuItem> o = mir.findById(item.getMenuItemId());
            if (!o.isPresent()) {
                throw new OrderServiceCustomException("Item missing in store menu", HttpStatus.NOT_FOUND);
            }
            MenuItem mi = o.get();
            if (mi.getAvailability() != null && !mi.getAvailability()) {
                throw new OrderServiceCustomException("Food item out of stock: " + mi.getName(), HttpStatus.GONE);
            }
        }
        
        Orders order = new Orders();
        order.setUserId(dto.getUserId());
        order.setRestaurantId(c.getRestaurantId());
        order.setTotalAmount(c.getTotalAmount());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        
        List<OrderItem> orderItemsList = new ArrayList<>();
        for(CartItem ci : c.getCartItems()) {
        	OrderItem orderItem = new OrderItem();
        	orderItem.setOrders(order);
        	orderItem.setMenuItemId(ci.getMenuItemId());
        	orderItem.setQuantity(ci.getQuantity());
        	orderItem.setSubtotal(ci.getSubtotal());
        	orderItem.setUnitPrice(ci.getUnitPrice());
        	
        	orderItemsList.add(orderItem);
        }
        	order.setOrderItems(orderItemsList);
        	Orders savedOrder = or.save(order);
        	
        	cir.deleteAll(c.getCartItems());
        	c.setTotalAmount(0.0);
        	cr.save(c);
        	
            OrderResponseDto response = new OrderResponseDto();
            response.setOrderId(savedOrder.getId());
            response.setTotalAmount(savedOrder.getTotalAmount());

		
		return response;
	}

}
