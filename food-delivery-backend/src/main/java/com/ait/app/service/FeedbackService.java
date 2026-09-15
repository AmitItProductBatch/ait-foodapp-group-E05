package com.ait.app.service;

import com.ait.app.requestbody.FeedbackRequestDto;
import com.ait.app.requestbody.FeedbackResponseDto;

public interface FeedbackService {
	
	FeedbackResponseDto createFeedback(FeedbackRequestDto dto);

}
