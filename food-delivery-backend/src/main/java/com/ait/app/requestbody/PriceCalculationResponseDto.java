package com.ait.app.requestbody;

public class PriceCalculationResponseDto {
	
	
	    private int itemId;
	    private int quantity;
	    private Double unitPrice;
	    private Double subtotal;

	    public int getItemId() {
	        return itemId;
	    }

	    public void setItemId(int itemId) {
	        this.itemId = itemId;
	    }

	    public int getQuantity() {
	        return quantity;
	    }

	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }

	    public Double getUnitPrice() {
	        return unitPrice;
	    }

	    public void setUnitPrice(Double unitPrice) {
	        this.unitPrice = unitPrice;
	    }

	    public Double getSubtotal() {
	        return subtotal;
	    }

	    public void setSubtotal(Double subtotal) {
	        this.subtotal = subtotal;
	    }

}
