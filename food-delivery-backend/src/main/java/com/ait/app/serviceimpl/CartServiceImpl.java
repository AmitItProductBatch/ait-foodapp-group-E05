package com.ait.app.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ait.app.exception.UserServiceCustomException;
import com.ait.app.model.Cart;
import com.ait.app.model.CartItem;
import com.ait.app.model.MenuItem;
import com.ait.app.model.User;
import com.ait.app.repository.CartRepository;
import com.ait.app.repository.MenuItemRepository;
import com.ait.app.repository.UserRepository;
import com.ait.app.requestbody.CartItemDetailsDto;
import com.ait.app.requestbody.CartRequestDto;
import com.ait.app.requestbody.CartResponseDto;
import com.ait.app.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public CartResponseDto addCart(CartRequestDto dto) {

        if (dto.getUserId() == null) {
            throw new UserServiceCustomException("UserId cannot be empty", HttpStatus.BAD_REQUEST);
        }

        Optional<User> user = userRepository.findById(dto.getUserId());

        if (!user.isPresent()) {
            throw new UserServiceCustomException("User not found", HttpStatus.NOT_FOUND);
        }

        if (cartRepository.existsByUserId(dto.getUserId())) {
            throw new UserServiceCustomException(
                    "Cart already exists for this customer",
                    HttpStatus.CONFLICT);
        }

        Cart cart = new Cart();
        cart.setUserId(dto.getUserId());
        cart.setRestaurantId(dto.getRestaurantId());
        cart.setTotalAmount(0.0);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());

        Cart savedCart = cartRepository.save(cart);

        return convertToResponse(savedCart, false);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {

        if (userId == null) {
            throw new UserServiceCustomException(
                    "UserId cannot be empty",
                    HttpStatus.BAD_REQUEST);
        }

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);

        if (!optionalCart.isPresent()) {
            throw new UserServiceCustomException(
                    "Cart not found for this user",
                    HttpStatus.NOT_FOUND);
        }

        Cart cart = optionalCart.get();

        // Ownership check.
        // In the current project there is no Spring Security/authentication
        // implementation, so userId is the owner identity supplied to this API.
        if (cart.getUserId() == null || !cart.getUserId().equals(userId)) {
            throw new UserServiceCustomException(
                    "You are not allowed to clear this cart",
                    HttpStatus.FORBIDDEN);
        }

        // orphanRemoval=true + cascade=ALL removes every CartItem from the database.
        if (cart.getCartItems() != null) {
            cart.getCartItems().clear();
        }

        // Reset the cart so it can be used for another restaurant.
        cart.setRestaurantId(0);
        cart.setTotalAmount(0.0);
        cart.setUpdatedAt(LocalDateTime.now());

        cartRepository.save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponseDto getCart(Long userId) {

        if (userId == null) {
            throw new UserServiceCustomException(
                    "UserId cannot be empty",
                    HttpStatus.BAD_REQUEST);
        }

        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);

        if (!optionalCart.isPresent()) {
            CartResponseDto responseDto = new CartResponseDto();
            responseDto.setId(null);
            responseDto.setUserId(userId);
            responseDto.setRestaurantId(0);
            responseDto.setTotalAmount(0.0);
            responseDto.setItems(new ArrayList<CartItemDetailsDto>());
            return responseDto;
        }

        Cart cart = optionalCart.get();
        return convertToResponse(cart, true);
    }

    private CartResponseDto convertToResponse(Cart cart, boolean includeItems) {

        CartResponseDto responseDto = new CartResponseDto();

        responseDto.setId(cart.getId());
        responseDto.setUserId(cart.getUserId());
        responseDto.setRestaurantId(cart.getRestaurantId());

        if (cart.getTotalAmount() == null) {
            responseDto.setTotalAmount(0.0);
        } else {
            responseDto.setTotalAmount(cart.getTotalAmount());
        }

        responseDto.setCreatedAt(cart.getCreatedAt());
        responseDto.setUpdatedAt(cart.getUpdatedAt());

        List<CartItemDetailsDto> itemDetailsList = new ArrayList<CartItemDetailsDto>();

        if (includeItems && cart.getCartItems() != null) {
            for (CartItem item : cart.getCartItems()) {

                CartItemDetailsDto itemDto = new CartItemDetailsDto();
                itemDto.setId(item.getId());
                itemDto.setMenuItemId(item.getMenuItemId());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setUnitPrice(item.getUnitPrice());
                itemDto.setSubtotal(item.getSubtotal());

                Optional<MenuItem> menuItem = menuItemRepository.findById(item.getMenuItemId());

                if (menuItem.isPresent()) {
                    itemDto.setName(menuItem.get().getName());
                } else {
                    itemDto.setName("Unknown Selection");
                }

                itemDetailsList.add(itemDto);
            }
        }

        responseDto.setItems(itemDetailsList);
        return responseDto;
    }
}
