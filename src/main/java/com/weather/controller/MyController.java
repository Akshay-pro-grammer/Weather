package com.weather.controller;

import com.weather.service.WeatherService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.weather.entities.ApiHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class MyController {
    @Autowired
    WeatherService weatherService;
    @GetMapping("/home")
    public String home(Model m) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        m.addAttribute("currentDateTime", formattedDateTime);
        m.addAttribute("apiHandler",new ApiHandler());
        return "home";
    }
    @PostMapping("/submit")
    public String submit(@ModelAttribute("apiHandler") ApiHandler apiHandler,Model model) {
        String city=apiHandler.getCity();
        JSONObject weatherJsonObject = weatherService.getWeather(city);
        if(weatherJsonObject!=null) {
        model.addAttribute("location",weatherJsonObject.getJSONObject("location"));
        model.addAttribute("current",weatherJsonObject.getJSONObject("current"));
        model.addAttribute("city",city);
        System.out.println(city);
        return "result";
        }else{
            return "home";
        }
    }
}
