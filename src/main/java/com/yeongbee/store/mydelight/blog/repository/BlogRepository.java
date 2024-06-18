package com.yeongbee.store.mydelight.blog.repository;


import com.yeongbee.store.mydelight.blog.domain.blog.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
}
