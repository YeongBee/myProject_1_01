package com.yeongbee.store.mydelight.ipconfig;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime localDateTime;

    private String accessPoint;

    private String country;

    private Boolean allow;

    public AccessLog(LocalDateTime localDateTime, String accessPoint, String country, Boolean allow) {
        this.localDateTime = localDateTime;
        this.accessPoint = accessPoint;
        this.country = country;
        this.allow = allow;
    }

    public AccessLog() {}
}
