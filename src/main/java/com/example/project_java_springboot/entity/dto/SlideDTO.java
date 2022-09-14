package com.example.project_java_springboot.entity.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SlideDTO {
    private int id;
    private String thumbnail;
    private String note;
    private String status;
}
