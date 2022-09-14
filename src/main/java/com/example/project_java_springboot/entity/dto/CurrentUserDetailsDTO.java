package com.example.project_java_springboot.entity.dto;

import com.example.project_java_springboot.entity.Roles;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrentUserDetailsDTO {
    private String id;
    private String username;
    private Collection<Roles> roles;
}
