package com.yeongbee.store.mydelight.weather.api;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Slf4j
public class WeatherApiIntegratedService {

    public String getWeatherJson(String urlParams, String urlKey) throws IOException {

        URL url = new URL(urlKey + urlParams);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public JSONArray getWeatherJsonArray(String response) throws  ParseException {

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        JSONObject responseObject = (JSONObject) jsonObject.get("response");
        JSONObject bodyObject = (JSONObject) responseObject.get("body");
        JSONObject itemsObject = (JSONObject) bodyObject.get("items");
        return (JSONArray) itemsObject.get("item");
    }
}
