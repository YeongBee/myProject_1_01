package com.yeongbee.store.mydelight.blog.domain.blog;


import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.domain.comment.Comment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
public class BlogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotNull(message = "공백 x")
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    private Account account;

    @OneToMany(mappedBy = "blog")
    private List<Comment> commentList = new ArrayList<>();


    public BlogEntity(String title, String content, LocalDateTime createdAt, Account account) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.account = account;
    }

    public BlogEntity() {}

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}
