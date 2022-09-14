package com.example.project_java_springboot.repository;

import com.example.project_java_springboot.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findAccountByUserName(String username);
    boolean existsAccountByUserName(String username);
}
