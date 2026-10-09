package com.ait.app.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.app.exception.CategoryAlreadyExistsException;
import com.ait.app.model.Category;
import com.ait.app.repository.CategoryRepo;
import com.ait.app.requestbody.CategoryRequestDto;
import com.ait.app.requestbody.CategoryResponseDto;
import com.ait.app.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	
	@Override
	public CategoryResponseDto addCategory(CategoryRequestDto request) {
		
		if(request.getCategoryname() == null ||
				request.getCategoryname().trim().isEmpty()) {
			throw new IllegalArgumentException(
					"Category name cannot be empty");
		
	}
		String categoryName = request.getCategoryname().trim();
		
		if(categoryRepo.existsByCategoryNameIgnoreCase(categoryName) ) {
			throw new CategoryAlreadyExistsException(
					"Category already exists: " + categoryName);
		}
		
		Category category = new Category();
		
		category.setCategoryName(categoryName.toUpperCase());
		category.setDescription(request.getDescription());
		category.setActive(request.isActive());
		
		Category savedCategory = categoryRepo.save(category);
		
		CategoryResponseDto response = new CategoryResponseDto();
		
		response.setId(savedCategory.getId());
		response.setCategoryName(savedCategory.getCategoryName());
		response.setDescription(savedCategory.getDescription());
		response.setActive(savedCategory.isActive());
		
		return response;

}}
