package com.yeongbee.store.mydelight.ipconfig;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;
import com.yeongbee.store.mydelight.ipconfig.adminpage.ban.BanIpService;
import com.yeongbee.store.mydelight.ipconfig.allow.AllowIpService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;

@Slf4j
public class GeoIPService {

    @Autowired private LogRepository logRepository;
    @Autowired private AllowIpService allowIpService;
    @Autowired private BanIpService banIpService;
    @Autowired private IpUtils ipUtils;

    private DatabaseReader dbReader;


    @PostConstruct
    public void init() throws IOException {
        dbReader = new DatabaseReader.Builder(new ClassPathResource("GeoLite2-Country.mmdb").getInputStream()).build();
    }

    public boolean isBlockedCountry(HttpServletRequest request){
        String clientip = ipUtils.extractClientIp(request);
        log.info("Clientip: " + clientip);

        if(allowIpService.findByIp(clientip)){
            return false;
        }

        if(clientip.equals("0:0:0:0:0:0:0:1")){
            return false;
        }

        try {
            InetAddress ipAddress = InetAddress.getByName(clientip);
            CountryResponse response = dbReader.country(ipAddress);
            Country country = response.getCountry();
            String countryCode = country.getIsoCode();

            log.info("clientip = {} , Country = {}" , clientip, countryCode);

            if(clientip.startsWith("34") || clientip.startsWith("35")){
                save(clientip,countryCode,countryCode.equals("KR"));
                return false;
            }

            if(allowIpService.findByIp(clientip)){
                save(clientip,"Home",true);
                return false;
            }

            if(banIpService.findByIp(clientip)){
                save(clientip,countryCode,false);
                return true;
            }

            save(clientip,countryCode,countryCode.equals("KR"));

            return !countryCode.equals("KR");

        } catch (IOException | GeoIp2Exception e){
            log.warn("Not Found Ip and Failed");
            save(clientip,"Null",false);
            return true;
        }
    }

    public void save(String accessPoint, String country, boolean  allow){
        AccessLog data = new AccessLog(LocalDateTime.now(), accessPoint, country, allow);
        logRepository.save(data);
    }
}
