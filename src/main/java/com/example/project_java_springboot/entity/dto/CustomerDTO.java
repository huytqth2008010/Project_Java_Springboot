package com.example.project_java_springboot.entity.dto;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Integer id;
    private String id_account;
    private String name;
    private String gender;
    private String email;
    private String address;
    private String phone_number;
}
