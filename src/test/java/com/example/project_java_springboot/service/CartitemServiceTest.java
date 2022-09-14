package com.example.project_java_springboot.service;

import com.example.project_java_springboot.repository.CartItemRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartitemServiceTest {
    public CartitemServiceTest(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    CartItemRepository cartItemRepository;
    @Test
    void deleteById() {

        System.out.println(cartItemRepository.deleteCartItem("product_1", "shopping_cart_1"));
    }
}