package com.yeongbee.store.mydelight.weather.myapi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class WeatherMyApiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime localDateTime;
    private double temperature; //온도
    private double humidity;    //시간
    private int soilMoisture;   // 토양습도


}
