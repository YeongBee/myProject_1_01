package com.yeongbee.store.mydelight.blog.service;


import com.yeongbee.store.mydelight.blog.domain.HasRole;
import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.domain.account.AccountDTO;
import com.yeongbee.store.mydelight.blog.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(AccountDTO accountDTO) {
        log.info(" account pass = {}", accountDTO.getPassword());
        Account account = new Account(accountDTO.getUsername(), accountDTO.getNickname(),
                passwordEncoder.encode(accountDTO.getPassword()), accountDTO.getEmail(), HasRole.USER.getValue());
        accountRepository.save(account);
    }

    public String checkUsername(String username) {

        if (username.length() < 6 || username.length() > 20) {
            return "char length";
        } else if(!username.matches("^[a-zA-Z0-9]{6,20}$")){
            return "no use";
        } else if (findByUsernameString(username).isPresent()) {
            return "name in use";
        } else{
            return "available";
        }
    }



    public Optional<Account> findByUsernameString(String username){
        return accountRepository.findByUsername(username);
    }

    public Optional<Account> findByEmail(String email){
        return accountRepository.findByEmail(email);
    }

    public Optional<Account> findByNickname(String nickName){
        return accountRepository.findByNickname(nickName);
    }


    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("Account with username " + username + " not found")
        );
    }


}
