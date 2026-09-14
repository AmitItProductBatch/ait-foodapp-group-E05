package com.ait.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ait.app.requestbody.FeedbackRequestDto;
import com.ait.app.requestbody.FeedbackResponseDto;
import com.ait.app.service.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
	
	@Autowired
	private FeedbackService feedbackService;
	
	@PostMapping
	public ResponseEntity<FeedbackResponseDto> createFeedback(
			@RequestBody FeedbackRequestDto dto) {
		
		FeedbackResponseDto response =
				feedbackService.createFeedback(dto);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	
	

}
