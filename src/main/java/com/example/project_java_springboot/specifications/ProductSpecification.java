package com.example.project_java_springboot.specifications;

import com.example.project_java_springboot.entity.Category;
import com.example.project_java_springboot.entity.Product;
import com.example.project_java_springboot.entity.search.SearchCriteria;
import com.example.project_java_springboot.entity.search.SearchCriteriaOperator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.*;

@Getter
@Setter
public class ProductSpecification extends GenericSpecifications<Product> {
    public ProductSpecification (SearchCriteria criteria){
        super(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (getCriteria().getOperation() == SearchCriteriaOperator.JOIN) {
            Join<Product, Category> productCategoryJoin = root.join("category");
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(productCategoryJoin.get(getCriteria().getKey())), "%" + getCriteria().getValue() + "%")
            );
        }

        return super.toPredicate(root, query, criteriaBuilder);
    }
}
