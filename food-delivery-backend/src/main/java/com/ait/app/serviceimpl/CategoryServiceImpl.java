package com.ait.app.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.app.exception.CategoryAlreadyExistsException;
import com.ait.app.exception.CategoryNotFoundException;
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

}


	@Override
	public CategoryResponseDto getCategory(int id) {
		
		 Category category = categoryRepo.findById(id).orElse(null);

		    if (category == null) {
		        throw new CategoryNotFoundException(
		                "Category not found with ID: " + id);
		    }

		    CategoryResponseDto response = new CategoryResponseDto();

		    response.setId(category.getId());
		    response.setCategoryName(category.getCategoryName());
		    response.setDescription(category.getDescription());
		    response.setActive(category.isActive());

		    return response;
	}


	@Override
	public List<CategoryResponseDto> getAllcategories() {
		
		 List<Category> categories = categoryRepo.findAll();

		    List<CategoryResponseDto> responseList = new ArrayList<>();

		    for (Category category : categories) {

		        CategoryResponseDto response = new CategoryResponseDto();

		        response.setId(category.getId());
		        response.setCategoryName(category.getCategoryName());
		        response.setDescription(category.getDescription());
		        response.setActive(category.isActive());

		        responseList.add(response);
		    }

		    return responseList;
	}


	@Override
	public List<CategoryResponseDto> getActiveCatgeories() {
		
		 List<Category> categories = categoryRepo.findByActiveTrue();

		    List<CategoryResponseDto> responseList = new ArrayList<>();

		    for (Category category : categories) {

		        CategoryResponseDto response = new CategoryResponseDto();

		        response.setId(category.getId());
		        response.setCategoryName(category.getCategoryName());
		        response.setDescription(category.getDescription());
		        response.setActive(category.isActive());

		        responseList.add(response);
		    }

		    return responseList;
	}}
