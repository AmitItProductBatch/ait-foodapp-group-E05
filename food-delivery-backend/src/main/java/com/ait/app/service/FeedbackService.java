package com.ait.app.service;

import com.ait.app.requestbody.FeedbackRequestDto;
import com.ait.app.requestbody.FeedbackResponseDto;
import com.ait.app.requestbody.FeedbackUpdateDto;

public interface FeedbackService {
	
	FeedbackResponseDto createFeedback(FeedbackRequestDto dto);
	
	FeedbackResponseDto submitFeedback(FeedbackRequestDto dto);
	
	FeedbackResponseDto updateFeedback(int feedbackId, FeedbackUpdateDto dto);
	
	void deleteFeedback(int id);

}
