package com.yeongbee.store.mydelight.blog.domain.account;

import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String nickname;

    private String password;

    @Column(unique = true)
    private String email;

    private String role;

    public Account(String username, String nickname, String password, String email, String role) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public Account() {}
}