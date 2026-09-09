package com.ait.app.requestbody;

public class AddressRequestDto {
	
	private String addressLabel;
    private String street;
    private String apartment;
    private String landmark;
    private String city;
    private String pincode;
    private String deliveryInstructions;
    private Long userId;
    
    
	public String getAddressLabel() {
		return addressLabel;
	}
	public void setAddressLabel(String addressLabel) {
		this.addressLabel = addressLabel;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getApartment() {
		return apartment;
	}
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getDeliveryInstructions() {
		return deliveryInstructions;
	}
	public void setDeliveryInstructions(String deliveryInstructions) {
		this.deliveryInstructions = deliveryInstructions;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
    
    

}