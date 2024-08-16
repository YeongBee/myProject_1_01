package com.yeongbee.store.mydelight.ipconfig.adminpage.ban;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanIpRepository extends JpaRepository<BanIp, Long> {
    boolean existsByIpName(String ip);
}
