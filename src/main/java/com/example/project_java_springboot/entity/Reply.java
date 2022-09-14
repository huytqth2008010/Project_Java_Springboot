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
@Table(name = "replys")
public class Reply  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    private int id_comment;
    @ManyToOne
    @JoinColumn(name = "id_comment", insertable = false, updatable = false)
    @JsonIgnore
    private Comment comment;


    private String id_account;
    @OneToOne
    @JoinColumn(name = "id_account", insertable = false, updatable = false)
    private Account account;

    private String id_product;
    @ManyToOne
    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    @JsonIgnore
    private Product product;

    @Enumerated(EnumType.ORDINAL)
    private EnumStatus status;
}
