package com.yeongbee.store.mydelight.mypage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsRepository extends JpaRepository<ContentsEntity, Long> {

}
