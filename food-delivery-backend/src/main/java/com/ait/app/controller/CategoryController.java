package com.ait.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.CategoryRequestDto;
import com.ait.app.requestbody.CategoryResponseDto;
import com.ait.app.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<CategoryResponseDto> addCategory(
			@RequestBody CategoryRequestDto request) {
		
		CategoryResponseDto response = categoryService.addCategory(request);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CategoryResponseDto> getCategory(
			@PathVariable int id) {
		
		CategoryResponseDto response = categoryService.getCategory(id);
		
		return new ResponseEntity<CategoryResponseDto>(response, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryResponseDto>> getAllcategories(){
		
		List<CategoryResponseDto> response = categoryService.getAllcategories();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/active")
	public ResponseEntity<List<CategoryResponseDto>> getActiveCategories(){
		
		List<CategoryResponseDto> response = categoryService.getActiveCatgeories();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
