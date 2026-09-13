package com.ait.app.requestbody;

public class MenuItemResponseDTO {
	
	    private int id;
	    private String name;
	    private String description;
	    private Double price;
	    private Boolean availability;
	    private String category;
	    private int restaurantId;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Double getPrice() {
			return price;
		}
		public void setPrice(Double price) {
			this.price = price;
		}
		public Boolean getAvailability() {
			return availability;
		}
		public void setAvailability(Boolean availability) {
			this.availability = availability;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public int getRestaurantId() {
			return restaurantId;
		}
		public void setRestaurantId(int  restaurantId) {
			this.restaurantId = restaurantId;
		}

	    
	}

