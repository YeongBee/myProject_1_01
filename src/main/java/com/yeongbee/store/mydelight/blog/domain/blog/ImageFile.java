package com.yeongbee.store.mydelight.blog.domain.blog;

import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String uploadFileName;
    private String storeFileName;

    public ImageFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public ImageFile() {
    }
}