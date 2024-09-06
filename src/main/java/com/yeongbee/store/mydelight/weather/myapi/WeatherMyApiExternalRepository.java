package com.yeongbee.store.mydelight.weather.myapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WeatherMyApiExternalRepository extends JpaRepository<WeatherMyApiEntityExternal, Long> {

    @Query("SELECT w FROM WeatherMyApiEntityExternal w ORDER BY w.localDateTime DESC LIMIT 1")
    Optional<WeatherMyApiEntityExternal> findLatestWeatherData();


    Page<WeatherMyApiEntityExternal> findAll(Pageable pageable);
    Page<WeatherMyApiEntityExternal> findWeatherMyApiEntityByLocalDateTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);


}
