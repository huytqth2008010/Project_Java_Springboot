package com.example.project_java_springboot.entity.search;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCriteria {
    private String key;
    private SearchCriteriaOperator operation;
    private Object value;
}
