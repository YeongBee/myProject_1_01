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
    private String password1;

    @NotNull(message = "공백 x")
    @Size(min = 8, max = 20)
    private String password2;

    @Column(unique = true)
    @NotNull(message = "공백 x")
    private String email;

    @NotNull(message = "공백 x")
    private String emailNum;

    public AccountDTO() {
    }

    public AccountDTO(String username, String nickname, String password1, String password2, String email) {
        this.username = username;
        this.nickname = nickname;
        this.password1 = password1;
        this.password2 = password2;
        this.email = email;
    }
}
