package com.ait.app.serviceimpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.FeedbackCustomException;
import com.ait.app.model.Feedback;
import com.ait.app.model.User;
import com.ait.app.repository.FeedbackRepo;
import com.ait.app.repository.UserRepository;
import com.ait.app.requestbody.FeedbackRequestDto;
import com.ait.app.requestbody.FeedbackResponseDto;
import com.ait.app.requestbody.FeedbackUpdateDto;
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

	@Override
	public FeedbackResponseDto submitFeedback(FeedbackRequestDto dto) {
		
		if (dto.getRating() < 1 || dto.getRating() > 5) {
			throw new FeedbackCustomException(
					"Rating must be between 1 and 5");
		}

		if (feedbackRepo.existsByUser_IdAndOrderId(
				dto.getUserId(), dto.getOrderId())) {

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
	
	@Override
	public FeedbackResponseDto updateFeedback(
	        int feedbackId,
	        FeedbackUpdateDto dto) {

	    Feedback feedback = feedbackRepo.findById(feedbackId)
	            .orElseThrow(() ->
	                    new FeedbackCustomException("Feedback not found"));

	    if (feedback.getUser() == null ||
	            !feedback.getUser().getId().equals(dto.getUserId())) {

	        throw new FeedbackCustomException(
	                "You are not authorized to edit this feedback",
	                HttpStatus.FORBIDDEN);
	    }

	    if (dto.getRating() != null) {

	        if (dto.getRating() < 1 || dto.getRating() > 5) {
	            throw new FeedbackCustomException(
	                    "Rating must be between 1 and 5");
	        }

	        feedback.setRating(dto.getRating());
	    }

	    if (dto.getComment() != null) {

	        if (dto.getComment().trim().isEmpty()) {
	            throw new FeedbackCustomException(
	                    "Comment cannot be empty");
	        }

	        feedback.setComment(dto.getComment());
	    }

	    feedback.setUpdatedAt(LocalDateTime.now());

	    Feedback updatedFeedback = feedbackRepo.save(feedback);

	    FeedbackResponseDto response = new FeedbackResponseDto();

	    response.setId(updatedFeedback.getId());
	    response.setUserId(updatedFeedback.getUser().getId());
	    response.setRestaurantId(updatedFeedback.getRestaurantId());
	    response.setOrderId(updatedFeedback.getOrderId());
	    response.setRating(updatedFeedback.getRating());
	    response.setComment(updatedFeedback.getComment());
	    response.setCreatedAt(updatedFeedback.getCreatedAt());
	    response.setUpdatedAt(updatedFeedback.getUpdatedAt());

	    return response;
	}
	
	@Override
	public void deleteFeedback(int id) {
		
		if(!feedbackRepo.existsById(id)) {
			
			throw new FeedbackCustomException("Feedback not found");
		}
		
		feedbackRepo.deleteById(id);
	}
}
	
	


