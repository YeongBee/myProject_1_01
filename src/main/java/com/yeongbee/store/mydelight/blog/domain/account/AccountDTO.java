package com.yeongbee.store.mydelight.blog.domain.account;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {

    @NotNull(message = "공백 x")
    private String username;

    @NotNull(message = "공백 x")
    private String nickname;

    @NotNull(message = "공백 x")
    private String password;

    @NotNull(message = "공백 x")
    private String email;

    @NotNull(message = "공백 x")
    private String role;

    public AccountDTO() {
    }

    public AccountDTO(String username, String nickname, String password, String email, String role) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
