package com.ait.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DeliveryFeeRule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double baseFee;
	private Double perKmRate;
	private Double maxDeliveryRadius;
	private Double freeDeliveryThreshold;
	private boolean active;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public void setFreeDeliveryRadius(Double freeDeliveryThreshold) {
		this.freeDeliveryThreshold = freeDeliveryThreshold;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Double getPerKmRate() {
		return perKmRate;
	}
	public void setPerKmRate(Double perKmRate) {
		this.perKmRate = perKmRate;
	}
	public void setFreeDeliveryThreshold(Double freeDeliveryThreshold) {
		this.freeDeliveryThreshold = freeDeliveryThreshold;
	}
	
	
	
	

}
