package com.yeongbee.store.mydelight.blog.domain.comment;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    @NotNull(message = "공백 x")
    private String content;

    @NotNull(message = "공백 x")
    private String username;

    private String location;

    public CommentDTO() {}

    public CommentDTO(String content, String username, String location) {
        this.content = content;
        this.username = username;
        this.location = location;
    }
}
