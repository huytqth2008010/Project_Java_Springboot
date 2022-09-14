package com.example.project_java_springboot.entity;

import com.example.project_java_springboot.entity.enums.MethodsConstant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String url;
    @Enumerated(EnumType.ORDINAL)
    private MethodsConstant method;
    @ManyToMany(mappedBy = "permissions")
    @JsonBackReference
    private Collection<Roles> roles;
}
