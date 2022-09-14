package com.example.project_java_springboot.specifications;

import com.example.project_java_springboot.entity.Account;
import com.example.project_java_springboot.entity.OrderDetail;
import com.example.project_java_springboot.entity.Product;
import com.example.project_java_springboot.entity.search.SearchCriteria;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.*;

@Getter
@Setter
public class OrderSpecification extends GenericSpecifications<Order> {


    public OrderSpecification(SearchCriteria criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (getCriteria().getOperation()) {
            case JOIN:
                switch (getCriteria().getKey()) {
                    case "product":
                        Join<OrderDetail, Product> orderDetailProductJoin = root.join("orderDetails").join("product");
                        return criteriaBuilder.like(criteriaBuilder.lower(orderDetailProductJoin.get("name")), "%" + getCriteria().getValue() + "%");
                    case "account":
                        Join<Order, Account> accountJoin = root.join("account");
                        return criteriaBuilder.like(criteriaBuilder.lower(accountJoin.get("userName")), "%" + getCriteria().getValue() + "%");
                }
        }
        return super.toPredicate(root, query, criteriaBuilder);
    }
}
