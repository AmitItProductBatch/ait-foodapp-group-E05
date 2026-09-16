package com.ait.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.app.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
	
	 boolean existsByName(String name);

}
