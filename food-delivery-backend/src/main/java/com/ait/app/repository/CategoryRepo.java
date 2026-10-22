package com.ait.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.app.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
	boolean existsByCategoryNameIgnoreCase(String categoryName);
	
	Optional<Category> findByIdAndActiveTrue(int id);
	
	List<Category> findByActiveTrue();

}
