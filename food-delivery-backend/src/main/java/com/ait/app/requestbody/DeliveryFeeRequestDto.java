package com.ait.app.requestbody;

public class DeliveryFeeRequestDto {
	
    private int restaurantAddressId;
    private int addressId;
    
	public int getRestaurantAddressId() {
		return restaurantAddressId;
	}
	public void setRestaurantAddressId(int restaurantAddressId) {
		this.restaurantAddressId = restaurantAddressId;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
    
    

}
