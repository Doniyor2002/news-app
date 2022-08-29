package com.example.news_app.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "RestPlaceHolder",url = "https://api.openweathermap.org/data/2.5/weather?q=Buxoro&appid=3880ea5d5526a3d21e4cb900bc19432a")
public interface JsonPlaceHolder {
    @GetMapping
    List<Weather> getAll();
}
