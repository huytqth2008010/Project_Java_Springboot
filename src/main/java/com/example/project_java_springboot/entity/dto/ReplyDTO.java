package com.example.project_java_springboot.entity.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReplyDTO {
    private Integer id;
    private String content;
    private int id_comment;
    private String id_account;
    private String id_product;
    private String status;
}
