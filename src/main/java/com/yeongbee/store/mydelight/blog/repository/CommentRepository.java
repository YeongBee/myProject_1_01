package com.yeongbee.store.mydelight.blog.repository;


import com.yeongbee.store.mydelight.blog.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
