package com.yeongbee.store.mydelight.ipconfig.adminpage;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class BanIp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String ipName;

    public BanIp(String ipName) {
        this.ipName = ipName;
    }

    public BanIp() {

    }
}
