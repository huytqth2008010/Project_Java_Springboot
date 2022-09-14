package com.example.project_java_springboot.entity;

import com.example.project_java_springboot.entity.base.BaseEntity;
import com.example.project_java_springboot.entity.enums.AccountStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(generator = "accountId")
    @GenericGenerator(name = "accountId", parameters = {@org.hibernate.annotations.Parameter(name = "prefix", value = "account"), @org.hibernate.annotations.Parameter(name = "tableName", value = "Account")},strategy = "com.example.project_java_springboot.generator.IdGenerator")
    private String id;
    private String userName;
    private String passwordHash;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Collection<Roles> roles;
    @Enumerated(EnumType.ORDINAL)
    private AccountStatus status;
}
