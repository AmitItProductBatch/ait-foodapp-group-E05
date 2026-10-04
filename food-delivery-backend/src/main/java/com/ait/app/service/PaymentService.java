package com.ait.app.service;

import com.ait.app.requestbody.PaymentOrderRequestDto;
import com.ait.app.requestbody.PaymentOrderResponseDto;

public interface PaymentService {
	
	PaymentOrderResponseDto createPaymentOrder(PaymentOrderRequestDto dto);

}
