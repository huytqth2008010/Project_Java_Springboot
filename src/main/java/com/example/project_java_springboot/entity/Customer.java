package com.example.project_java_springboot.entity;

import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.entity.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String id_account;
    @ManyToOne
    @JoinColumn(name = "id_account", insertable = false, updatable = false)
    private Account account;

    private String name;
    private String gender;
    private String email;
    private String address;
    private String phone_number;

    @Enumerated(EnumType.ORDINAL)
    private EnumStatus status;
}
