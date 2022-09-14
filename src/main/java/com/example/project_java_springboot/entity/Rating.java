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
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double rating_star;

    private String id_product;
    @ManyToOne
    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    @JsonIgnore
    private Product product;

    private String id_account;
    @OneToOne
    @JoinColumn(name = "id_account", insertable = false, updatable = false)
    private Account account;

    @Enumerated(EnumType.ORDINAL)
    private EnumStatus status;
}
