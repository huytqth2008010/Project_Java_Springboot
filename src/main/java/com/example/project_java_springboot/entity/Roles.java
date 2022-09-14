package com.example.project_java_springboot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(generator = "roleId")
    @GenericGenerator(name = "roleId", parameters = {@org.hibernate.annotations.Parameter(name = "prefix", value = "role"), @org.hibernate.annotations.Parameter(name = "tableName", value = "Roles")},strategy = "com.example.project_java_springboot.generator.IdGenerator")
    private String id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Collection<Account> accounts;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Collection<Permission> permissions;
}
