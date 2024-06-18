package com.yeongbee.store.mydelight.blog.service;

import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.domain.account.AccountDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired AccountService accountService;

    @Test
    void accounts(){
        AccountDTO accountDTO = new AccountDTO("admin", "admin",
                "0000", "google@gmail.com", "ROLE_ADMIN");

        accountService.save(accountDTO);

        Account admin = accountService.findByUsername("admin");


        Assertions.assertThat(admin.getUsername()).isEqualTo(accountDTO.getUsername());


    }

}