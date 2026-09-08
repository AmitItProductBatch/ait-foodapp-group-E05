package com.ait.app.repository;

import com.ait.app.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
//	 @Query(value = "select * from users where email = :email", nativeQuery = true)
//	 boolean findUserByEmail(@Param("email") String email);
	
	boolean existsByEmail(String email); 

}



