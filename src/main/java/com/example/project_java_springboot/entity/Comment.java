package com.example.project_java_springboot.entity;

import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.entity.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;
    private  String id_account;
    private String id_product;

    @Enumerated(EnumType.ORDINAL)
    private EnumStatus status;

    @ManyToOne
    @JoinColumn(name = "id_account", insertable = false, updatable = false)
    @JsonIgnore
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    @JsonIgnore
    private Product product;


}
