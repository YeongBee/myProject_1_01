package com.yeongbee.store.mydelight.blog.domain;

import lombok.Getter;

@Getter
public enum HasRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

    HasRole(String value) {
        this.value = value;
    }
}
