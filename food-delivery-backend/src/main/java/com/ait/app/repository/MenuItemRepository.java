package com.ait.app.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ait.app.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem,Integer> {

	Boolean existsByRestaurantIdAndName(int restaurantId, String name);

//	@Query(value = "SELECT * FROM menu_item WHERE id = :id", nativeQuery = true)
//    Optional<MenuItem> getById(@Param("id") Long id);
	
	
}
