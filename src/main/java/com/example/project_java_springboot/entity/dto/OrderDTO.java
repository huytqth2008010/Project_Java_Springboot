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
    private String shipName;
    private String shipAddress;
    private String shipPhone;
    private String shipNote;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;

}
