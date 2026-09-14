package com.ait.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.app.model.Feedback;

public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {
	
	boolean existsByUserIdAndOrderId(Long userId, int orderId);
	
	

}
