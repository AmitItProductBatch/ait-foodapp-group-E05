package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
