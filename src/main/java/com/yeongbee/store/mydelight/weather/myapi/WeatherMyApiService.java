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
import java.util.*;



@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherMyApiService {

    private final WeatherMyApiRepository weatherMyApiRepository;
    private final RestTemplate restTemplate;

    @Value("${weather.my.api}")
    private String url;


    @PostConstruct
    @Scheduled(cron = "0 0,30 * * * ?")
//    @Scheduled(fixedRate = 1000)
    public void save() throws UnsupportedEncodingException {

        try {
            ResponseEntity<WeatherMyApiEntity> response = restTemplate.getForEntity(url, WeatherMyApiEntity.class);
            WeatherMyApiEntity weatherMyApi = response.getBody();
            weatherMyApi.setLocalDateTime(LocalDateTime.now());

            weatherMyApiRepository.save(weatherMyApi);
//            log.info("weatherService={}", response.getBody().toString());
            log.info("weatherMyAPIService Success");

        } catch (NullPointerException e) {
            e.printStackTrace();
            log.error("WeatherMyApi is NULL");
            log.error("weatherService Failed");
        }catch (Exception e){
            e.printStackTrace();
            log.error("WeatherMyAPI Exception = {}", e.getMessage());
        }

    }

    public WeatherMyApiEntity findLatestData() {
        try {
            Optional<WeatherMyApiEntity> optional = weatherMyApiRepository.findLatestWeatherData();
            return optional.orElseGet(WeatherMyApiEntity::new);
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }

     return null;
    }

    public Page<WeatherMyApiEntity> findAll(int page) {
      Pageable pageable = PageRequest.of(page, 15, Sort.by("localDateTime").descending());
      return weatherMyApiRepository.findAll(pageable);
    }

    public Page<WeatherMyApiEntity> findByBetween(LocalDateTime start, LocalDateTime end, int page) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("localDateTime").descending());
        return weatherMyApiRepository.findWeatherMyApiEntityByLocalDateTimeBetween(start, end, pageable);
    }



}
