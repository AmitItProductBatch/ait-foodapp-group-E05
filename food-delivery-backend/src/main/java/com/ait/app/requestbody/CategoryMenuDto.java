package com.ait.app.requestbody;

import java.util.List;

public class CategoryMenuDto {

	private String category;
	private List<MenuItemResponseDTO> items;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<MenuItemResponseDTO> getItems() {
		return items;
	}

	public void setItems(List<MenuItemResponseDTO> items) {
		this.items = items;
	}

}