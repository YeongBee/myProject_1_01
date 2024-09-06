package com.yeongbee.store.mydelight.weather.myapi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Entity
@Setter
@NoArgsConstructor
public class WeatherMyApiEntityExternal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime localDateTime;
    private double temperature; //온도
    private double humidity;    //시간
    private int soilMoisture;   // 토양습도


}
