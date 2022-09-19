package com.example.project_java_springboot.entity.dto;

import lombok.*;

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
    private String status;

}
