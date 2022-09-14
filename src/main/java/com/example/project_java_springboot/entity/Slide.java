package com.example.project_java_springboot.entity;

import com.example.project_java_springboot.entity.enums.EnumStatus;
import com.example.project_java_springboot.entity.enums.ProductStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "slides")
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String thumbnail;
    private String note;

    @Enumerated(EnumType.ORDINAL)
    private EnumStatus status;
}
