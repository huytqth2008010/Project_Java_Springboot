package com.example.project_java_springboot.service;


import com.example.project_java_springboot.entity.CartItem;
import com.example.project_java_springboot.entity.CartItemId;
import com.example.project_java_springboot.entity.Category;
import com.example.project_java_springboot.entity.ShoppingCart;
import com.example.project_java_springboot.repository.CartItemRepository;
import com.example.project_java_springboot.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartitemService {

    private final CartItemRepository cartItemRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    public CartitemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }
    public CartItem findCardItem(CartItemId cartItemId) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if (optionalCartItem.isPresent()) {
            return optionalCartItem.get();
        } else {
            return null;
        }
    }
    public boolean deleteCartItem(CartItemId cartItemId) {
        cartItemRepository.deleteCartItem(cartItemId.getProductId(), cartItemId.getShoppingCartId());
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(cartItemId.getShoppingCartId());
        if (!shoppingCartOptional.isPresent())
            return false;
        ShoppingCart shoppingCart = shoppingCartOptional.get();
        shoppingCart.updateTotalPrice();
        shoppingCartRepository.save(shoppingCart);
        return true;
    }
}
