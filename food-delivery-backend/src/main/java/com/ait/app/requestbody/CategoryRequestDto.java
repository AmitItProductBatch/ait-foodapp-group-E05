package com.ait.app.requestbody;

public class CategoryRequestDto {
	
	private String categoryName;
	private String description;
	private boolean active = true;
	
	public String getCategoryname() {
		return categoryName;
	}
	public void setCategoryname(String categoryname) {
		this.categoryName = categoryname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
