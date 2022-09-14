package com.example.project_java_springboot.entity;

import com.example.project_java_springboot.entity.base.BaseEntity;
import com.example.project_java_springboot.entity.enums.ProductStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String thumbnail;
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status;
//    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
//    @JsonManagedReference
//    private Set<Product> products;
}
