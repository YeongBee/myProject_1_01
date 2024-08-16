package com.yeongbee.store.mydelight.ipconfig.allow;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class AllowIp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String ipName;

    private String content;

    public AllowIp(String ipName, String content) {
        this.ipName = ipName;
        this.content = content;
    }


    public AllowIp() {

    }
}
