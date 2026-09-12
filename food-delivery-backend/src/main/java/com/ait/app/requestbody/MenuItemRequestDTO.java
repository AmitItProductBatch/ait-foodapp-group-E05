package com.ait.app.requestbody;

public class MenuItemRequestDTO {
	    
	    private String name;
	    private String description;
	    private Double price;
	    private Boolean availability;
	    private String category;
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

}


