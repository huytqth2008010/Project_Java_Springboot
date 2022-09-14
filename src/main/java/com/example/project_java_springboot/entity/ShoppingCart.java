package com.example.project_java_springboot.entity;

import com.example.project_java_springboot.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity {
    @Id
    @GeneratedValue(generator = "shoppingCartId")
    @GenericGenerator(name = "shoppingCartId", parameters = {@Parameter(name = "prefix", value = "shopping_cart"), @Parameter(name = "tableName", value = "ShoppingCart")}, strategy = "com.example.project_java_springboot.generator.IdGenerator")
    private String id;
    private String accountId;
    @OneToOne
    @JoinColumn(name = "accountId", insertable = false, updatable = false)
    @JsonBackReference
    private Account account;
    private BigDecimal totalPrice;//1
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shoppingCart", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE})
    @JsonManagedReference
    private Set<CartItem> cartItems;//thằng này bị xóa thì thằng 1 phải đổi giá trị

    public void addTotalPrice(CartItem cartItem) {
        if (this.totalPrice == null) {
            this.totalPrice = new BigDecimal(0);
        }

        BigDecimal quantity = new BigDecimal(cartItem.getQuantity());
        if(cartItem.getStatus() == 1){//cái status này lấy từ tú à
            //đr a
//             nó vô dụng ở đây
            this.totalPrice = this.totalPrice.add(cartItem.getUnitPrice().multiply(quantity));
        }
    }
    public void updateTotalPrice(){
        this.totalPrice = new BigDecimal(0);
        List<CartItem> cartItemList = new ArrayList<>(cartItems);
        for (CartItem item: cartItemList
             ) {
            totalPrice= totalPrice.add((item.getUnitPrice().multiply(new BigDecimal(item.getQuantity()))));
        }
    }



    /*WTF SAO KO XUOGNOS DC */


}
