package com.yeongbee.store.mydelight.blog.service;


import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntity;
import com.yeongbee.store.mydelight.blog.domain.comment.Comment;
import com.yeongbee.store.mydelight.blog.domain.comment.CommentDTO;
import com.yeongbee.store.mydelight.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogService blogService;

    public Comment save(CommentDTO commentDTO, Long blogId) {
        BlogEntity blog = blogService.findById(blogId);
        Comment comment = new Comment(commentDTO.getContent(), commentDTO.getUsername(),
                commentDTO.getLocation(), LocalDateTime.now(),blog);
         commentRepository.save(comment);
        return comment;
    }

    public void delete(Long id) {
        Boolean exists = commentRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("댓글이 없습니다");
        }
        commentRepository.deleteById(id);
    }
}
