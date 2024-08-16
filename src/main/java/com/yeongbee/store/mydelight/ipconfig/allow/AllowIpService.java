package com.yeongbee.store.mydelight.ipconfig.allow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AllowIpService {
    private final AllowIpRepository repository;

    public String save(String ip,String content) {
        AllowIp allowIp = new AllowIp(ip, content);
        repository.save(allowIp);
        return ip;
    }

    public Boolean findByIp(String ip) {
        return repository.existsByIpName(ip);
    }
}
