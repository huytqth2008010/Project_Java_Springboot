package com.example.project_java_springboot.repository;

import com.example.project_java_springboot.entity.CartItem;
import com.example.project_java_springboot.entity.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cart_item WHERE cart_item.product_id =:product_id AND cart_item.shopping_cart_id =:shopping_cart_id",nativeQuery = true)
    void deleteCartItem(@Param("product_id") String product_id,@Param("shopping_cart_id") String shopping_cart_id);
}

