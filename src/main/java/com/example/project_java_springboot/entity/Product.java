package com.example.project_java_springboot.entity;

import com.example.project_java_springboot.entity.base.BaseEntity;
import com.example.project_java_springboot.entity.enums.ProductStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(generator = "productId")
    @GenericGenerator(name = "productId", parameters = {@Parameter(name = "prefix", value = "product"), @Parameter(name = "tableName", value = "Product")},strategy = "com.example.project_java_springboot.generator.IdGenerator")
    private String id;
    private String name;
    private String thumbnail;
    private String description;
    private BigDecimal cost_price;
    private BigDecimal unit_price;
    private BigDecimal promotion_price;
    private int qty;
    private String slug;

    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status;

    private Integer categoryId;
    @ManyToOne
    @JoinColumn(name = "categoryId", insertable = false, updatable = false)
    private Category category;
//    @JsonManagedReference
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private Set<OrderDetail> orderDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
