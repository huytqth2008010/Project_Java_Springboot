package com.example.project_java_springboot.controller;

import com.example.project_java_springboot.entity.CartItem;
import com.example.project_java_springboot.entity.CartItemId;
import com.example.project_java_springboot.service.CartitemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/cartitems")
public class CartItemController {
    private final CartitemService cartitemService;

    public CartItemController(CartitemService cartitemService) {
        this.cartitemService = cartitemService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public boolean deleteById(@RequestBody CartItemId id) {
        return cartitemService.deleteCartItem(id);
    }
}
