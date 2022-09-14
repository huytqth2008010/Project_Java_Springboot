package com.example.project_java_springboot.entity.dto;

import com.example.project_java_springboot.entity.enums.AccountStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDTO {
    private String userName;
    private String passwordHash;
}
