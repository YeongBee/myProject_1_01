package com.yeongbee.store.mydelight.ipconfig.allow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowIpRepository extends JpaRepository<AllowIp, Long> {
    boolean existsByIpName(String ip);
}
