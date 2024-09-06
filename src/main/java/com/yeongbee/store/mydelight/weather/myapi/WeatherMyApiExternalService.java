package com.yeongbee.store.mydelight.weather.myapi;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherMyApiExternalService {

    private final WeatherMyApiExternalRepository weatherMyApiExternalRepository;
    private final RestTemplate restTemplate;

    @Value("${weather.my.api}")
    private String url;


    @PostConstruct
    @Scheduled(cron = "0 0,30 * * * ?")
//    @Scheduled(fixedRate = 1000)
    public void save() throws UnsupportedEncodingException {

        try {
            ResponseEntity<WeatherMyApiEntityExternal> response = restTemplate.getForEntity(url, WeatherMyApiEntityExternal.class);
            WeatherMyApiEntityExternal weatherMyApi = response.getBody();
            weatherMyApi.setLocalDateTime(LocalDateTime.now());

            weatherMyApiExternalRepository.save(weatherMyApi);
//            log.info("weatherService={}", response.getBody().toString());
            log.info("weatherMyAPIExternalService Success");

        } catch (NullPointerException e) {
            e.printStackTrace();
            log.error("WeatherMyApiExternal is NULL");
            log.error("weatherExternalService Failed");
        }catch (Exception e){
            e.printStackTrace();
            log.error("WeatherMyAPIExternal Exception = {}", e.getMessage());
        }

    }

    public WeatherMyApiEntityExternal findLatestData() {

        try {
            Optional<WeatherMyApiEntityExternal> optional = weatherMyApiExternalRepository.findLatestWeatherData();
            return optional.orElseGet(WeatherMyApiEntityExternal::new);
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return null;
    }

    public Page<WeatherMyApiEntityExternal> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("localDateTime").descending());
        return weatherMyApiExternalRepository.findAll(pageable);
    }

    public Page<WeatherMyApiEntityExternal> findByBetween(LocalDateTime start, LocalDateTime end, int page) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("localDateTime").descending());
        return weatherMyApiExternalRepository.findWeatherMyApiEntityByLocalDateTimeBetween(start, end, pageable);
    }


}
