package com.ait.app.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
import com.ait.app.requestbody.OrderDetailsDto;
import com.ait.app.requestbody.OrderHistoryDto;
import com.ait.app.requestbody.OrderItemDetailsDto;
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
    @Transactional(isolation = Isolation.SERIALIZABLE) 
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
        	c.getCartItems().clear();  
        	c.setTotalAmount(0.0);
        	c.setRestaurantId(0);
        	cr.save(c);
        	
            OrderResponseDto response = new OrderResponseDto();
            response.setOrderId(savedOrder.getId());
            response.setTotalAmount(savedOrder.getTotalAmount());

		
		return response;
	}
	
	@Override
	@Transactional(readOnly = true)
	public OrderDetailsDto getOrderDetails(Long orderId) {

	    Optional<Orders> optionalOrder = or.findById(orderId);

	    if (!optionalOrder.isPresent()) {
	        throw new OrderServiceCustomException(
	                "Order not found",
	                HttpStatus.NOT_FOUND);
	    }

	    Orders order = optionalOrder.get();

	    OrderDetailsDto response = new OrderDetailsDto();

	    response.setOrderId(order.getId());
	    response.setUserId(order.getUserId());
	    response.setRestaurantId(order.getRestaurantId());
	    response.setDeliveryAddress(order.getDeliveryAddress());

	    response.setTotalAmount(order.getTotalAmount());

	    response.setStatus(order.getStatus());
	    response.setPaymentStatus(order.getPaymentStatus());
	    response.setPaymentMethod(order.getPaymentMethod());

	    response.setCreatedAt(order.getCreatedAt());
	    response.setUpdatedAt(order.getUpdatedAt());

	    List<OrderItemDetailsDto> itemDetails = new ArrayList<>();

	    if (order.getOrderItems() != null) {

	        for (OrderItem orderItem : order.getOrderItems()) {

	            OrderItemDetailsDto itemDto = new OrderItemDetailsDto();

	            itemDto.setItemId(orderItem.getId());
	            itemDto.setMenuItemId(orderItem.getMenuItemId());
	            itemDto.setQuantity(orderItem.getQuantity());
	            itemDto.setUnitPrice(orderItem.getUnitPrice());
	            itemDto.setSubtotal(orderItem.getSubtotal());

	            Optional<MenuItem> optionalMenuItem =
	                    mir.findById(orderItem.getMenuItemId());

	            if (optionalMenuItem.isPresent()) {
	                itemDto.setItemName(optionalMenuItem.get().getName());
	            }

	            itemDetails.add(itemDto);
	        }
	    }

	    response.setItems(itemDetails);

	    return response;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<OrderHistoryDto> getOrderHistory(
	        Long userId,
	        int page,
	        int size,
	        String status,
	        LocalDateTime fromDate,
	        LocalDateTime toDate) {

	    if (userId == null) {
	        throw new OrderServiceCustomException(
	                "User id is required",
	                HttpStatus.BAD_REQUEST);
	    }

	    if (page < 0) {
	        throw new OrderServiceCustomException(
	                "Page number cannot be negative",
	                HttpStatus.BAD_REQUEST);
	    }

	    if (size <= 0) {
	        throw new OrderServiceCustomException(
	                "Page size must be greater than zero",
	                HttpStatus.BAD_REQUEST);
	    }

	    if (size > 100) {
	        throw new OrderServiceCustomException(
	                "Page size cannot be greater than 100",
	                HttpStatus.BAD_REQUEST);
	    }

	    if (fromDate != null && toDate != null
	            && fromDate.isAfter(toDate)) {

	        throw new OrderServiceCustomException(
	                "From date cannot be after to date",
	                HttpStatus.BAD_REQUEST);
	    }

	    Pageable pageable = PageRequest.of(
	            page,
	            size,
	            Sort.by(
	                    Sort.Direction.DESC,
	                    "createdAt"));

	    Page<Orders> ordersPage;

	    boolean hasStatus = status != null && !status.trim().isEmpty();
	    boolean hasFromDate = fromDate != null;
	    boolean hasToDate = toDate != null;

	    if (!hasStatus && !hasFromDate && !hasToDate) {

	        ordersPage = or.findByUserId(
	                userId,
	                pageable);

	    } else if (hasStatus && !hasFromDate && !hasToDate) {

	        ordersPage = or.findByUserIdAndStatus(
	                userId,
	                status,
	                pageable);

	    } else if (!hasStatus && hasFromDate && hasToDate) {

	        ordersPage = or.findByUserIdAndCreatedAtBetween(
	                userId,
	                fromDate,
	                toDate,
	                pageable);

	    } else if (hasStatus && hasFromDate && hasToDate) {

	        ordersPage = or.findByUserIdAndStatusAndCreatedAtBetween(
	                userId,
	                status,
	                fromDate,
	                toDate,
	                pageable);

	    } else if (!hasStatus && hasFromDate) {

	        ordersPage = or.findByUserIdAndCreatedAtGreaterThanEqual(
	                userId,
	                fromDate,
	                pageable);

	    } else if (!hasStatus && hasToDate) {

	        ordersPage = or.findByUserIdAndCreatedAtLessThanEqual(
	                userId,
	                toDate,
	                pageable);

	    } else if (hasStatus && hasFromDate) {

	        ordersPage = or.findByUserIdAndStatusAndCreatedAtGreaterThanEqual(
	                userId,
	                status,
	                fromDate,
	                pageable);

	    } else {

	        ordersPage = or.findByUserIdAndStatusAndCreatedAtLessThanEqual(
	                userId,
	                status,
	                toDate,
	                pageable);
	    }

	    return ordersPage.map(order -> {

	        OrderHistoryDto dto = new OrderHistoryDto();

	        dto.setOrderId(order.getId());
	        dto.setUserId(order.getUserId());
	        dto.setRestaurantId(order.getRestaurantId());
	        dto.setTotalAmount(order.getTotalAmount());
	        dto.setStatus(order.getStatus());
	        dto.setPaymentStatus(order.getPaymentStatus());
	        dto.setPaymentMethod(order.getPaymentMethod());
	        dto.setCreatedAt(order.getCreatedAt());
	        dto.setUpdatedAt(order.getUpdatedAt());

	        return dto;
	    });
	}

}
