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
/*        AccountDTO accountDTO = new AccountDTO("admin", "admin",
                "admin0011", "yeongbee@yeongbee.store", "ROLE_ADMIN");*/

    AccountDTO accountDTO = new AccountDTO("user", "user",
                "user1234", "user@yeongbee.store", "ROLE_USER");

        accountService.save(accountDTO);

        Account admin = accountService.findByUsername("user");


        Assertions.assertThat(admin.getUsername()).isEqualTo(accountDTO.getUsername());


    }

}