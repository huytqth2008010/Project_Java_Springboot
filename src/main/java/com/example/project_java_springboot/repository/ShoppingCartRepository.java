package com.example.project_java_springboot.repository;

import com.example.project_java_springboot.entity.CartItem;
import com.example.project_java_springboot.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String>, JpaSpecificationExecutor<ShoppingCart> {
    Optional<ShoppingCart> findByAccount_UserName(String userName);

}
