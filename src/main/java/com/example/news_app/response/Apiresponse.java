package com.example.news_app.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Apiresponse<T> {
    private String message;
    private boolean succes;
    private T data;
}
