package com.example.project_java_springboot.entity.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CartItemDTO {
    private String productId;
    private Integer quantity;
    private Integer status = 1;
}
