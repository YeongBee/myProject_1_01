package com.yeongbee.store.mydelight.blog.domain.blog;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BlogEntityDTO {

    @Column(length = 200)
   @NotNull(message = "공백 x")
    private String title;

    @NotNull(message = "공백 x")
    private String content;

    public BlogEntityDTO() {}

    public BlogEntityDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
