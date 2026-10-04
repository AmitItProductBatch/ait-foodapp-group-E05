package com.ait.app.serviceimpl;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.app.model.Orders;
import com.ait.app.model.Payment;
import com.ait.app.repository.OrderRepository;
import com.ait.app.repository.PaymentRepo;
import com.ait.app.requestbody.PaymentOrderRequestDto;
import com.ait.app.requestbody.PaymentOrderResponseDto;
import com.ait.app.service.PaymentService;
import com.razorpay.RazorpayClient;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepo paymentRepo;

    @Override
    public PaymentOrderResponseDto createPaymentOrder(
            PaymentOrderRequestDto dto) {

        Orders order = orderRepository.findOrderById(dto.getOrderId());

        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        // Amount is taken from existing Order
        Double amountInRupees = order.getTotalAmount();

        if (amountInRupees == null || amountInRupees <= 0) {
            throw new RuntimeException(
                    "Order amount must be greater than zero");
        }

        try {

            // Application uses Rupees.
            // Convert to Paise only when sending to Razorpay.
            int amountInPaise =
                    (int) Math.round(amountInRupees * 100);

            JSONObject options = new JSONObject();

            options.put("amount", amountInPaise);
            options.put("currency", "INR");
            options.put(
                    "receipt",
                    "order_" + order.getId());

            com.razorpay.Order razorpayOrder =
                    razorpayClient.orders.create(options);

            String razorpayOrderId =
                    razorpayOrder.get("id");

            // Store payment details
            Payment payment = new Payment();

            payment.setOrderId(order.getId());
            payment.setRazorpayOrderId(razorpayOrderId);
            payment.setAmount(amountInRupees);
            payment.setCurrency("INR");
            payment.setStatus("CREATED");
            payment.setPaymentDate(LocalDateTime.now());
            payment.setPaymentMethod("RAZORPAY");

            paymentRepo.save(payment);

            // Prepare response
            PaymentOrderResponseDto response =
                    new PaymentOrderResponseDto();

            response.setOrderId(order.getId());
            response.setRazorpayOrderId(razorpayOrderId);
            response.setAmount(amountInRupees);
            response.setCurrency("INR");
            response.setStatus("CREATED");
            response.setPaymentDate(payment.getPaymentDate());
            response.setPaymentMethod(payment.getPaymentMethod());

            return response;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Payment order creation failed: "
                    + e.getMessage());
        }
    }
}