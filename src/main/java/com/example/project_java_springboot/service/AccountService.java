package com.example.project_java_springboot.service;

import com.example.project_java_springboot.entity.Account;
import com.example.project_java_springboot.entity.enums.AccountStatus;
import com.example.project_java_springboot.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(String id) {
        return accountRepository.findById(id);
    }

    public boolean existsAccount(String userName) {
        return accountRepository.existsAccountByUserName(userName);
    }

    public Optional<Account> findByUsername(String username) {
        return accountRepository.findAccountByUserName(username);
    }

    public Account save(Account account) {
        account.setPasswordHash(passwordEncoder.encode(account.getPasswordHash()));
        account.setStatus(AccountStatus.ACTIVE);

        return accountRepository.save(account);
    }

    public void deleteById(String id) {
        accountRepository.deleteById(id);
    }
}
