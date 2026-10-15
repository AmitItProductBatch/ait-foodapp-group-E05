package com.ait.app.service;

import com.ait.app.requestbody.CategoryRequestDto;
import com.ait.app.requestbody.CategoryResponseDto;

public interface CategoryService {
	
	CategoryResponseDto addCategory(CategoryRequestDto request);

}
