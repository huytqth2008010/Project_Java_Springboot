package com.example.project_java_springboot.service;

import com.example.project_java_springboot.entity.*;
import com.example.project_java_springboot.entity.dto.CartItemDTO;
import com.example.project_java_springboot.entity.dto.ShoppingCartDTO;
import com.example.project_java_springboot.entity.enums.CartItemStatus;
import com.example.project_java_springboot.repository.AccountRepository;
import com.example.project_java_springboot.repository.ProductRepository;
import com.example.project_java_springboot.repository.ShoppingCartRepository;
import com.example.project_java_springboot.until.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ShoppingCartService {
    private final ModelMapper modelMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final AuthenticationService authenticationService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, AccountRepository accountRepository, AuthenticationService authenticationService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
        this.authenticationService = authenticationService;
        this.modelMapper = new ModelMapper();
    }

    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }

    public Optional<ShoppingCart> findById(String id) {
        return shoppingCartRepository.findById(id);
    }

    public Optional<ShoppingCart> findByAccountUserName(String userName){
        return shoppingCartRepository.findByAccount_UserName(userName);
    }

    public ShoppingCart save(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = modelMapper.map(shoppingCartDTO, ShoppingCart.class);
        Optional<Account> optionalAccount = accountRepository.findAccountByUserName(CurrentUser.getCurrentUser().getName());
        if (!optionalAccount.isPresent()){
            return null;
        }
        Account account = optionalAccount.get();
        shoppingCart.setAccountId(account.getId());
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByAccount_UserName(account.getUserName());
        if (optionalShoppingCart.isPresent()) {
            shoppingCart = optionalShoppingCart.get();
            shoppingCart.setTotalPrice(new BigDecimal(0));
        }
        Set<CartItem> setCartItem = new HashSet<>();
        for (CartItemDTO cartItemDTO :
                shoppingCartDTO.getCartItemDTOSet()) {
            Optional<Product> optionalProduct = productRepository.findById(cartItemDTO.getProductId());
            if (!optionalProduct.isPresent()) {
                break;
            }

            if (cartItemDTO.getQuantity() <= 0){
                cartItemDTO.setStatus(-1);
            }

            Product product = optionalProduct.get();
            CartItem cartItem = CartItem.builder()
                    .id(new CartItemId(shoppingCart.getId(), product.getId()))
                    .productName(product.getName())
                    .productThumbnail(product.getThumbnail())
                    .quantity(cartItemDTO.getQuantity())
                    .unitPrice(product.getUnit_price())
                    .shoppingCart(shoppingCart)
                    .status(cartItemDTO.getStatus())
                    .cartItemStatus(CartItemStatus.of(cartItemDTO.getStatus()))
                    .build();
            shoppingCart.addTotalPrice(cartItem); // add tổng giá bigdecimal
            setCartItem.add(cartItem);
        }
        shoppingCart.setCartItems(setCartItem);
        shoppingCart.setCreatedBy(authenticationService.getCurrentUser().getUser().getId());
        shoppingCart.setUpdatedBy(authenticationService.getCurrentUser().getUser().getId());
        return shoppingCartRepository.save(shoppingCart);

    }

    public void deleteById(String id) {
        shoppingCartRepository.deleteById(id);
    }
}
