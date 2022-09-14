package com.example.project_java_springboot.entity;


import com.example.project_java_springboot.entity.dto.CurrentUserDetailsDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrentUserDetails {
    private CurrentUserDetailsDTO user;
}
