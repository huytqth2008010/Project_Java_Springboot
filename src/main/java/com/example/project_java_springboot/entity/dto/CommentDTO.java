package com.example.project_java_springboot.entity.dto;

import com.example.project_java_springboot.entity.enums.EnumStatus;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer id;
    private String content;
    private  String id_account;
    private String id_product;
    private String status;
}
