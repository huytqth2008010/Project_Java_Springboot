package com.example.project_java_springboot.entity.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RatingDTO {
    private Integer id;
    private Double rating_star;
    private  String id_account;
    private String id_product;
    private String status;
}
