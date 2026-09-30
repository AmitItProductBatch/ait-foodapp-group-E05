package com.ait.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ait.app.model.DeliveryFeeRule;

@Repository
public interface DeliveryFeeRuleRepo extends JpaRepository<DeliveryFeeRule, Long> {
	
	Optional<DeliveryFeeRule> findFirstByActiveTrueOrderByIdDesc();

}
