package com.ait.app.serviceimpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.app.exception.FeedbackCustomException;
import com.ait.app.model.Feedback;
import com.ait.app.model.User;
import com.ait.app.repository.FeedbackRepo;
import com.ait.app.repository.UserRepository;
import com.ait.app.requestbody.FeedbackRequestDto;
import com.ait.app.requestbody.FeedbackResponseDto;
import com.ait.app.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepo feedbackRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public FeedbackResponseDto createFeedback(FeedbackRequestDto dto) {
		
		if(dto.getRating() <1 || dto.getRating() > 5) {
			throw new FeedbackCustomException(
					"Rating must be between 1 and 5");
		}
		if(feedbackRepo.existsByUser_IdAndOrderId(dto.getUserId(),
				dto.getOrderId())) {
			
			throw new FeedbackCustomException(
					"Feedback already exists for this order");
		}
		
		User user = userRepository.findById(dto.getUserId()).orElse(null);
		
		if (user == null) {
			throw new FeedbackCustomException(
					"User not found");
		}
		
		Feedback feedback = new Feedback();
		
		feedback.setUser(user);
		feedback.setRestaurantId(dto.getRestaurantId());
		feedback.setOrderId(dto.getOrderId());
		feedback.setRating(dto.getRating());
		feedback.setComment(dto.getComment());
		
		feedback.setCreatedAt(LocalDateTime.now());
		feedback.setUpdatedAt(LocalDateTime.now());
		
		Feedback savedFeedback = feedbackRepo.save(feedback);
		
		FeedbackResponseDto response = new FeedbackResponseDto();
		
		response.setId(savedFeedback.getId());
		response.setUserId(savedFeedback.getUser().getId());
		response.setRestaurantId(savedFeedback.getRestaurantId());
		response.setOrderId(savedFeedback.getOrderId());
		response.setRating(savedFeedback.getRating());
		response.setComment(savedFeedback.getComment());
		response.setCreatedAt(savedFeedback.getCreatedAt());
		response.setUpdatedAt(savedFeedback.getUpdatedAt());
		
		return response;
		
	}
		
	}
	
	


