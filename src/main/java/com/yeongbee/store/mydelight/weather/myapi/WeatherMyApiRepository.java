package com.yeongbee.store.mydelight.weather.myapi;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface WeatherMyApiRepository extends JpaRepository<WeatherMyApiEntity, Long> {

    @Query("SELECT w FROM WeatherMyApiEntity w ORDER BY w.localDateTime DESC LIMIT 1")
    Optional<WeatherMyApiEntity> findLatestWeatherData();

//    List<WeatherMyApiEntity> findWeatherMyApiEntityByLocalDateTimeBetween(LocalDateTime start, LocalDateTime end);

    Page<WeatherMyApiEntity> findAll(Pageable pageable);
    Page<WeatherMyApiEntity> findWeatherMyApiEntityByLocalDateTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

}
