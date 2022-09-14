package com.example.project_java_springboot.entity.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDTO {
    private String id;
    private String name;
    private String thumbnail;
    private String description;
    private BigDecimal unit_price;
    private BigDecimal promotion_price;
    private int qty;
    private String categoryName;
    private String status;
}
