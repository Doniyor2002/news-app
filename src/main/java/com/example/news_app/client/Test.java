package com.example.news_app.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class Test {
    private final JsonPlaceHolder jsonPlaceHolder;
    @GetMapping
    public ResponseEntity<?> getAllWeather(){
        return ResponseEntity.ok().body(jsonPlaceHolder.getAll());
    }
}
