package com.ait.app.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.app.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem,Integer> {

	Boolean existByRestaurantIdAndName(Integer restaurantID,String name);

	
	
}
