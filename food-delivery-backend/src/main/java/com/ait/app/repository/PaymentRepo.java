package com.ait.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.app.model.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

}
