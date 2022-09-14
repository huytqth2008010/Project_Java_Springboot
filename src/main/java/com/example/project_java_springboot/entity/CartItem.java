package com.example.project_java_springboot.entity;

import com.example.project_java_springboot.entity.enums.CartItemStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart_item")
public class CartItem {
    @EmbeddedId
    private CartItemId id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id);
    }
// chỗ logic kia đâu
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @MapsId("shoppingCartId")
    @JoinColumn(name = "shopping_cart_id")
    @JsonBackReference
    private ShoppingCart shoppingCart;
//
//    @ManyToOne
//    @MapsId("productId")
//    @JoinColumn(name = "product_id")
//    @JsonBackReference
//    private Product product;

    private String productName;
    private String productThumbnail;
    private Integer quantity;
    private BigDecimal unitPrice;
    @Basic
    private int status;
    @Transient
    private CartItemStatus cartItemStatus;

    @PostLoad
    void fillTransient(){
        this.cartItemStatus = CartItemStatus.of(this.status);
    }
}
