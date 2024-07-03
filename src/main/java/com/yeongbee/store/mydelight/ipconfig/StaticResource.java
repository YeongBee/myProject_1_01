package com.yeongbee.store.mydelight.ipconfig;


import lombok.Getter;

@Getter
public enum StaticResource {


    CSS("/css"),
    JS("/js"),
    MY("/my"),
    ERRORS("/errors"),
    BLOGS("/blogs"),
    FAVICON("/favicon"),
    IMAGE("/blog/images");

    private final String path;

    StaticResource(String path) {
        this.path = path;
    }

}
