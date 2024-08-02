package com.yeongbee.store.mydelight.ipconfig.adminpage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BanIpService {
    private final BanIpRepository repository;

    public String save(String ip) {
        BanIp banIp = new BanIp(ip);
        repository.save(banIp);
        return ip;
    }

    public Boolean findByIp(String ip) {
        return repository.existsByIpName(ip);
    }
}
