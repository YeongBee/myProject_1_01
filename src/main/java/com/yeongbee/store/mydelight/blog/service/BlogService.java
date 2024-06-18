package com.yeongbee.store.mydelight.blog.service;


import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntity;
import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntityDTO;
import com.yeongbee.store.mydelight.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public void save(BlogEntityDTO blogEntityDTO, Account account) {
        BlogEntity blogEntity = new BlogEntity(blogEntityDTO.getTitle(),blogEntityDTO.getContent(),
                LocalDateTime.now(),account);
        blogRepository.save(blogEntity);
    }

    public List<BlogEntity> findAll() {
        return blogRepository.findAll();
    }

    public BlogEntity findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new NoSuchElementException("블로그가 없습니다"));
    }


    // 변경 감지 기능을 통해 DB에 반영
    public void update(BlogEntityDTO blogEntityDTO, Long id) {
        BlogEntity blogEntity = findById(id);
        blogEntity.update(blogEntityDTO.getTitle(), blogEntityDTO.getContent());
        blogRepository.save(blogEntity);
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }


}
