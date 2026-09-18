package com.ait.app.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ait.app.exception.PriceCustomException;
import com.ait.app.model.MenuItem;
import com.ait.app.repository.MenuItemRepository;
import com.ait.app.requestbody.PriceCalculationRequestDto;
import com.ait.app.requestbody.PriceCalculationResponseDto;
import com.ait.app.service.PriceService;

@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	MenuItemRepository menuItemRepository;

	@Override
	public PriceCalculationResponseDto calculatePrice(PriceCalculationRequestDto dto) {

		if (dto.getQuantity() <= 0) {

			throw new PriceCustomException("Quantity must be greater than zero", HttpStatus.BAD_REQUEST);
		}

		Optional<MenuItem> items = menuItemRepository.findById(dto.getItemId());

		if (!items.isPresent()) {

			throw new PriceCustomException("Menu item not found", HttpStatus.NOT_FOUND);
		}

		MenuItem item = items.get();

		Double unitPrice = item.getPrice();

		Double subtotal = unitPrice * dto.getQuantity();

		PriceCalculationResponseDto response = new PriceCalculationResponseDto();

		response.setItemId(item.getId());
		response.setQuantity(dto.getQuantity());
		response.setUnitPrice(unitPrice);
		response.setSubtotal(subtotal);

		return response;

	}

}
