package com.yeongbee.store.mydelight.ipconfig;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;
import com.yeongbee.store.mydelight.ipconfig.adminpage.BanIpService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    LogRepository logRepository;

    @Autowired
    BanIpService banIpService;

    @Autowired
    IpUtils ipUtils;

    private DatabaseReader dbReader;

    @Value("${check.access}")
    private String checkAccess;

    @Value("${check.access1}")
    private String checkAccess1;

    @Value("${check.access2}")
    private String checkAccess2;

    @Value("${check.access3}")
    private String checkAccess3;



    public GeoIPService() {}


    @PostConstruct
    public void init() throws IOException {
        dbReader = new DatabaseReader.Builder(new ClassPathResource("GeoLite2-Country.mmdb").getInputStream()).build();

    }
    public boolean isBlockedCountry(HttpServletRequest request){
        String clientip = ipUtils.extractClientIp(request);
//        log.info("Clientip: " + clientip);


        if(clientip.equals(checkAccess) || clientip.equals(checkAccess1) ||
                clientip.equals(checkAccess2) ||clientip.equals(checkAccess3)){
            return false;
        }



        try {
            InetAddress ipAddress = InetAddress.getByName(clientip);
            CountryResponse response = dbReader.country(ipAddress);
            Country country = response.getCountry();
            String countryCode = country.getIsoCode();

            log.info("clientip = {} , Country = {}" , clientip, countryCode);


            if(banIpService.findByIp(clientip)){
                save(clientip,countryCode,false);
                return true;
            }

            save(clientip,countryCode,countryCode.equals("KR"));
            return !countryCode.equals("KR");

        } catch (IOException | GeoIp2Exception e){
            log.info("Failed");
            save(clientip,"Null",false);
            return true;
        }
    }

    public void save(String accessPoint, String country, boolean  allow){
        AccessLog data = new AccessLog(LocalDateTime.now(), accessPoint, country, allow);
        logRepository.save(data);
    }
}
