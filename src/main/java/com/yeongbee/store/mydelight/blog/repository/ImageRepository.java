package com.yeongbee.store.mydelight.blog.repository;

import com.yeongbee.store.mydelight.blog.domain.blog.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageFile, Long> {
}
