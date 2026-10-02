package com.ait.app.requestbody;

public class DeliveryFeeRuleRequestDto {
	
	private Double baseFee;
	private Double perKmRate;
	private Double maxDeliveryRadius;
	private Double freeDeliveryThreshold;
	public Double getBaseFee() {
		return baseFee;
	}
	public void setBaseFee(Double baseFee) {
		this.baseFee = baseFee;
	}
	
	public Double getMaxDeliveryRadius() {
		return maxDeliveryRadius;
	}
	public void setMaxDeliveryRadius(Double maxDeliveryRadius) {
		this.maxDeliveryRadius = maxDeliveryRadius;
	}
	public Double getFreeDeliveryThreshold() {
		return freeDeliveryThreshold;
	}
	public void setFreeDeliveryThreshold(Double freeDeliveryThreshold) {
		this.freeDeliveryThreshold = freeDeliveryThreshold;
	}
	public Double getPerKmRate() {
		return perKmRate;
	}
	public void setPerKmRate(Double perKmRate) {
		this.perKmRate = perKmRate;
	}
	
	
	
	

}
