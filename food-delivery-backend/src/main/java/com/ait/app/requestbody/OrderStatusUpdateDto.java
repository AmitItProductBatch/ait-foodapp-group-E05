package com.ait.app.requestbody;

import com.ait.app.model.OrderStatus;

import jakarta.validation.constraints.NotNull;

public class OrderStatusUpdateDto {

	  @NotNull
	    private OrderStatus status;

	    public OrderStatus getStatus() {
	        return status;
	    }

	    public void setStatus(OrderStatus status) {
	        this.status = status;
	    }
}
