package com.ait.app.service;

import java.util.List;

import com.ait.app.requestbody.CategoryRequestDto;
import com.ait.app.requestbody.CategoryResponseDto;

public interface CategoryService {
	
	CategoryResponseDto addCategory(CategoryRequestDto request);
	
	CategoryResponseDto getCategory(int id);
	
	List<CategoryResponseDto> getAllcategories();
	
	List<CategoryResponseDto> getActiveCatgeories();

}
