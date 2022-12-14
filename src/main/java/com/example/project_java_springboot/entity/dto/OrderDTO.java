package com.example.project_java_springboot.entity.dto;

import com.example.project_java_springboot.entity.enums.OrderStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDTO {
    private String payment_method;
    private String shipName;
    private String shipAddress;
    private String shipPhone;
    private String shipNote;
    private String status;

}
