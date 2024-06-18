package com.yeongbee.store.mydelight.blog.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginForm {

    @NotNull(message = "공백 x")
    private String username;

    @NotNull(message = "공백 x")
    private String password;

}
