package com.example.project_java_springboot.entity.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ShoppingCartDTO {
    private String id;
    private BigDecimal totalPrice;
    private Set<CartItemDTO> cartItemDTOSet;
}
