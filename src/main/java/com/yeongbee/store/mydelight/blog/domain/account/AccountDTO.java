package com.yeongbee.store.mydelight.blog.domain.account;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {

    @NotNull(message = "공백 x")
    @Column(unique = true)
    @Size(min = 6, max = 20)
    private String username;

    @Column(unique = true)
    @Size(min = 4, max = 15)
    private String nickname;

    @NotNull(message = "공백 x")
    @Size(min = 8, max = 20)
    private String password;

    @Column(unique = true)
    @NotNull(message = "공백 x")
    private String email;

    public AccountDTO() {
    }

    public AccountDTO(String username, String nickname, String password, String email) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }
}
