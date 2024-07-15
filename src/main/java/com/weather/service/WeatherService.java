package com.weather.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {
    //getWeather
    public JSONObject getWeather(String city) {
        var api_key="0ee0e45aa1724322aa461418241507";
        var url="http://api.weatherapi.com/v1/current.json?key="+api_key+"&q="+city;
        var request=HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client= HttpClient.newBuilder().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200){
                return new JSONObject(response.body());
            }
            else {
                throw new RuntimeException("Failed to fetch weather data: " + response.statusCode());
            }
        } catch (Exception e) {
            return null;
        }
    }
}
