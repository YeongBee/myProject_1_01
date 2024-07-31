package com.yeongbee.store.mydelight.blog.service;

import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.domain.account.AccountDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountServiceTest {

    @Autowired AccountService accountService;

    @Test
    void accounts(){
        AccountDTO accountDTO1 = new AccountDTO("admin", "admin",
                "admin0011", "admin0011", "yeongbee@yeongbee.store");

    AccountDTO accountDTO2 = new AccountDTO("user", "user",
                "user1234", "user1234", "user@yeongbee.store");

    AccountDTO accountDTO3 = new AccountDTO("qwerqwer", "qwerqwer",
                "qwerqwer", "qwerqwer", "qwer@yeongbee.store");
/*
        accountService.save(accountDTO1);
        accountService.save(accountDTO2);*/
        accountService.save(accountDTO3);

        Account name1 = accountService.findByUsername("admin");
        Account name2 = accountService.findByUsername("user");


        Assertions.assertThat(name1.getUsername()).isEqualTo(accountDTO1.getUsername());
        Assertions.assertThat(name2.getUsername()).isEqualTo(accountDTO2.getUsername());


    }

}