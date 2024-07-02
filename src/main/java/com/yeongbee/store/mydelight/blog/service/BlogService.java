package com.yeongbee.store.mydelight.blog.service;

import com.yeongbee.store.mydelight.blog.domain.FIleStore;
import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntity;
import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntityDTO;
import com.yeongbee.store.mydelight.blog.domain.blog.ImageFile;
import com.yeongbee.store.mydelight.blog.domain.blog.UploadFile;
import com.yeongbee.store.mydelight.blog.repository.BlogRepository;
import com.yeongbee.store.mydelight.blog.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {

    private final BlogRepository blogRepository;
    private final ImageRepository imageRepository;
    private final FIleStore filestore;


    @Transactional
    public BlogEntity save(BlogEntityDTO blogEntityDTO, Account account) throws IOException {

/*
        List<UploadFile> files = fIleStore.storeFiles(blogEntityDTO.getImageFile());


        log.info("ImageList={}", files.toString());

        BlogEntity blogEntity = new BlogEntity(blogEntityDTO.getTitle(),blogEntityDTO.getContent(),
                LocalDateTime.now(),account, files);

        for (UploadFile file : files) {
            file.setBlog(blogEntity);
        }*/

        BlogEntity blogEntity = new BlogEntity(blogEntityDTO.getTitle(),blogEntityDTO.getContent(),
                LocalDateTime.now(),account);




        return blogRepository.save(blogEntity);
    }

    public List<Map<String, String>> imagesave(List<MultipartFile> images) {

        try {
//            List<UploadFile> uploadFiles = filestore.storeFiles(images);
            List<UploadFile> uploadFiles = new ArrayList<>();
            for (MultipartFile file : images) {
                // 파일 확장자 검사
                String originalFileName = file.getOriginalFilename();
                String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();

                if (isAllowedExtension(extension)) {
                    UploadFile uploadFile = filestore.storeFile(file);
                    uploadFiles.add(uploadFile);
                } else {
                    // 허용되지 않는 확장자의 경우 건너뛰기
                    log.error("Skipping file with unsupported extension: {}", originalFileName);
                }
            }

            List<Map<String, String>> response = new ArrayList<>();
            for (UploadFile uploadFile : uploadFiles) {
                Map<String, String> fileResponse = new HashMap<>();
                fileResponse.put("originalFileName", uploadFile.getUploadFileName());
                fileResponse.put("savedFileName", uploadFile.getStoreFileName());
                response.add(fileResponse);
            }

            List<ImageFile> imageFiles = uploadFiles.stream()
                    .map(file -> new ImageFile(file.getUploadFileName(), file.getStoreFileName()))
                    .toList();

            imageRepository.saveAll(imageFiles);
            return response;

        }catch (IOException e){
            return Collections.emptyList();
        }
    }

    private boolean isAllowedExtension(String extension) {
        List<String> allowedExtensions = Arrays.asList("jpeg", "jpg", "png", "gif", "webp", "svg");
        return allowedExtensions.contains(extension);
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
