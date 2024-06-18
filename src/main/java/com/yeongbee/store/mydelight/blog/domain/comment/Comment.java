package com.yeongbee.store.mydelight.blog.domain.comment;



import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;


@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotNull(message = "공백 x")
    private String content;

    @NotNull(message = "공백 x")
    private String username;

    @NotNull(message = "공백 x")
    private String location;

    private LocalDateTime createdAt;

    @ManyToOne
    private BlogEntity blog;


    public Comment(String content, String username, String location, LocalDateTime createdAt, BlogEntity blog) {
        this.content = content;
        this.username = username;
        this.location = location;
        this.createdAt = createdAt;
        this.blog = blog;
    }

    public Comment() {}


}
