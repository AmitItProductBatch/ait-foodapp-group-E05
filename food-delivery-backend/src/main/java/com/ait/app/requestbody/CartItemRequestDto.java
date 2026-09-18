package com.ait.app.requestbody;

public class CartItemRequestDto {
	
	private Long cartId;
    private int menuItemId;
    private int quantity;
    
    
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	
	public int getMenuItemId() {
		return menuItemId;
	}
	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
    
    
	
	   

}
