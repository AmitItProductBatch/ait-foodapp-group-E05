package com.ait.app.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.FeedbackCustomException;
import com.ait.app.model.Feedback;
import com.ait.app.model.Restaurant;
import com.ait.app.model.User;
import com.ait.app.repository.FeedbackRepo;
import com.ait.app.repository.RestaurantRepository;
import com.ait.app.repository.UserRepository;
import com.ait.app.requestbody.FeedbackPageResponseDto;
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

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public FeedbackResponseDto createFeedback(FeedbackRequestDto dto) {

		if (dto.getRating() < 1 || dto.getRating() > 5) {
			throw new FeedbackCustomException("Rating must be between 1 and 5");
		}

		if (feedbackRepo.existsByUser_IdAndOrderId(dto.getUserId(), dto.getOrderId())) {

			throw new FeedbackCustomException("Feedback already exists for this order");
		}

		User user = userRepository.findById(dto.getUserId()).orElse(null);

		if (user == null) {
			throw new FeedbackCustomException("User not found");
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
			throw new FeedbackCustomException("Rating must be between 1 and 5");
		}

		if (feedbackRepo.existsByUser_IdAndOrderId(dto.getUserId(), dto.getOrderId())) {

			throw new FeedbackCustomException("Feedback already exists for this order");
		}

		User user = userRepository.findById(dto.getUserId()).orElse(null);

		if (user == null) {
			throw new FeedbackCustomException("User not found");
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
	public FeedbackResponseDto updateFeedback(int feedbackId, FeedbackUpdateDto dto) {

		Feedback feedback = feedbackRepo.findById(feedbackId)
				.orElseThrow(() -> new FeedbackCustomException("Feedback not found"));

		if (feedback.getUser() == null || !feedback.getUser().getId().equals(dto.getUserId())) {

			throw new FeedbackCustomException("You are not authorized to edit this feedback", HttpStatus.FORBIDDEN);
		}

		if (dto.getRating() != null) {

			if (dto.getRating() < 1 || dto.getRating() > 5) {
				throw new FeedbackCustomException("Rating must be between 1 and 5");
			}

			feedback.setRating(dto.getRating());
		}

		if (dto.getComment() != null) {

			if (dto.getComment().trim().isEmpty()) {
				throw new FeedbackCustomException("Comment cannot be empty");
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

		if (!feedbackRepo.existsById(id)) {
			throw new FeedbackCustomException("Feedback not found");
		}

		feedbackRepo.deleteById(id);
	}

	@Override
	public FeedbackPageResponseDto getRestaurantFeedback(int restaurantId, int page, int size) {

		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new FeedbackCustomException("Restaurant not found", HttpStatus.NOT_FOUND));

		if (page < 0 || size <= 0) {
			throw new FeedbackCustomException("Page must be 0 or greater and size must be positive");
		}

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

		Page<Feedback> feedbackPage = feedbackRepo
				.findByRestaurantIdAndPublishedTrueAndDeletedFalseAndFlaggedFalse(restaurantId, pageable);

		List<FeedbackResponseDto> feedbackList = feedbackPage.getContent().stream().map(feedback -> {

			FeedbackResponseDto dto = new FeedbackResponseDto();

			dto.setId(feedback.getId());

			if (feedback.getUser() != null) {
				dto.setUserId(feedback.getUser().getId());
			}

			dto.setRestaurantId(feedback.getRestaurantId());
			dto.setOrderId(feedback.getOrderId());
			dto.setRating(feedback.getRating());
			dto.setComment(feedback.getComment());
			dto.setCreatedAt(feedback.getCreatedAt());
			dto.setUpdatedAt(feedback.getUpdatedAt());

			return dto;

		}).collect(Collectors.toList());

		FeedbackPageResponseDto response = new FeedbackPageResponseDto();

		response.setFeedback(feedbackList);
		response.setCurrentPage(feedbackPage.getNumber());
		response.setTotalPages(feedbackPage.getTotalPages());
		response.setTotalElements(feedbackPage.getTotalElements());
		response.setPageSize(feedbackPage.getSize());

		return response;
	}
}
