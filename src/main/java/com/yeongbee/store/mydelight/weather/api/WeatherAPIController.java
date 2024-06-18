package com.yeongbee.store.mydelight.weather.api;


import com.yeongbee.store.mydelight.weather.myapi.WeatherMyApiEntity;
import com.yeongbee.store.mydelight.weather.myapi.WeatherMyApiService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weather")
@Slf4j
public class WeatherAPIController {

    private final WeatherShtService shtService;
    private final WeatherMidService midService;
    private final WeatherMyApiService myApiService;

    @GetMapping("")
    public String weatherShows(Model model) {

        List<WeatherSetEntity> weatherSht = shtService.getWeatherShtSet();
        weatherSht.addAll(midService.getWeatherMidSet());
        WeatherUSN weatherUSN = shtService.getWeatherUsn();
        List<WeatherForecastAPI> fiveWeather = shtService.getShtFiveWeather();
        WeatherMyApiEntity myApiEntity = myApiService.findLatestData();

        model.addAttribute("weatherUSN", weatherUSN);
        model.addAttribute("weatherSets", weatherSht);
        model.addAttribute("weatherFive", fiveWeather);
        model.addAttribute("myApiEntity", myApiEntity);

        return "weather/weather_view";
    }


    @GetMapping("/list")
    public String getTest(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(value = "startDate", required = false) LocalDate startDate,
                          @RequestParam(value = "endDate", required = false) LocalDate endDate) {

        Page<WeatherMyApiEntity> selectList;

        if (startDate != null && endDate != null) {

            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atStartOfDay();
            selectList = myApiService.findByBetween(startDateTime, endDateTime, page);

        } else {
            selectList = myApiService.findAll(page);
        }


        model.addAttribute("selectList", selectList);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);


        return "weather/weather_list";
    }

}
