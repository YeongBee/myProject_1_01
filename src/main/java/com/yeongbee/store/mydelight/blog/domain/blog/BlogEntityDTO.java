package com.yeongbee.store.mydelight.blog.domain.blog;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
@Setter
public class BlogEntityDTO {

    @Column(length = 200)
   @NotNull(message = "공백 x")
    private String title;

    @NotNull(message = "공백 x")
    private String content;

    private List<MultipartFile> imageFile;
    private MultipartFile thumb;

    public BlogEntityDTO() {}

    public BlogEntityDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
