package com.yeongbee.store.mydelight.blog.service;

import com.yeongbee.store.mydelight.blog.domain.FIleStore;
import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntity;
import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntityDTO;
import com.yeongbee.store.mydelight.blog.domain.blog.UploadFile;
import com.yeongbee.store.mydelight.blog.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileStore;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BlogService {

    private static final Logger log = LoggerFactory.getLogger(BlogService.class);
    private final BlogRepository blogRepository;
    private final FIleStore fIleStore;

    @Transactional
    public BlogEntity save(BlogEntityDTO blogEntityDTO, Account account) throws IOException {

        List<UploadFile> files = fIleStore.storeFiles(blogEntityDTO.getImageFile());

        log.info("ImageList={}", files.toString());

        BlogEntity blogEntity = new BlogEntity(blogEntityDTO.getTitle(),blogEntityDTO.getContent(),
                LocalDateTime.now(),account, files);

        for (UploadFile file : files) {
            file.setBlog(blogEntity);
        }

        return blogRepository.save(blogEntity);
    }

    public List<BlogEntity> findAll() {
        return blogRepository.findAll();
    }

    public BlogEntity findById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new NoSuchElementException("블로그가 없습니다"));
    }


    // 변경 감지 기능을 통해 DB에 반영
    @Transactional
    public void update(BlogEntityDTO blogEntityDTO, Long id) {
        BlogEntity blogEntity = findById(id);
        blogEntity.update(blogEntityDTO.getTitle(), blogEntityDTO.getContent());
    }

    @Transactional
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }


}
