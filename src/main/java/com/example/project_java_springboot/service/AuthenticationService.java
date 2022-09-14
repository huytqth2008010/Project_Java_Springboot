package com.example.project_java_springboot.service;

import com.example.project_java_springboot.entity.Account;
import com.example.project_java_springboot.entity.CurrentUserDetails;
import com.example.project_java_springboot.entity.Roles;
import com.example.project_java_springboot.entity.dto.CurrentUserDetailsDTO;
import com.example.project_java_springboot.entity.dto.RegisterDTO;
import com.example.project_java_springboot.entity.enums.AccountStatus;
import com.example.project_java_springboot.repository.AccountRepository;
import com.example.project_java_springboot.repository.RolesRepository;
import com.example.project_java_springboot.until.CurrentUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private final RolesRepository rolesRepository;

    public AuthenticationService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, RolesRepository rolesRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findAccountByUserName(username);
        if (!optionalAccount.isPresent()){
            throw new UsernameNotFoundException("Invalid information.");
        }
        Account existsAccount = optionalAccount.get();
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Roles r:
                existsAccount.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        }

        return new User(
                existsAccount.getUserName(), existsAccount.getPasswordHash(), authorities);
    }

//    public Account register(Account account){
//        account.setPasswordHash(passwordEncoder.encode(account.getPasswordHash()));
//        account.setStatus(AccountStatus.ACTIVE);
//        return accountRepository.save(account);
//    }

    public Account register(RegisterDTO registerDTO){
        Account account = new Account();
        account.setUserName(registerDTO.getUserName());
        account.setPasswordHash(passwordEncoder.encode(registerDTO.getPasswordHash()));
        account.setRoles(Collections.singletonList(rolesRepository.findByName("user").get()));
        account.setStatus(AccountStatus.ACTIVE);
        return accountRepository.save(account);
    }
    public CurrentUserDetails getCurrentUser(){
        Optional<Account> optionalAccount = accountRepository.findAccountByUserName(CurrentUser.getCurrentUser().getName());

        if (!optionalAccount.isPresent()){
            return null;
        }

        Account account = optionalAccount.get();

        CurrentUserDetailsDTO currentUserDetailsDTO = new CurrentUserDetailsDTO();
        currentUserDetailsDTO.setId(account.getId());
        currentUserDetailsDTO.setUsername(account.getUserName());
        currentUserDetailsDTO.setRoles(account.getRoles());

        return new CurrentUserDetails(currentUserDetailsDTO);
    }
}
