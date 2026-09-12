package com.ait.app.repository;

import java.awt.MenuItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {

	Boolean existByRestaurantIdAndName(Integer restaurantID,String name);
}
