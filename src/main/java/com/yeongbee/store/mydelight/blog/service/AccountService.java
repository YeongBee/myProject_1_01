package com.yeongbee.store.mydelight.blog.service;


import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.domain.account.AccountDTO;
import com.yeongbee.store.mydelight.blog.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(AccountDTO accountDTO) {
        Account account = new Account(accountDTO.getUsername(), accountDTO.getNickname(),
                passwordEncoder.encode(accountDTO.getPassword()), accountDTO.getEmail(), "ADMIN");
        accountRepository.save(account);
    }



    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("Account with username " + username + " not found")
        );
    }

}
