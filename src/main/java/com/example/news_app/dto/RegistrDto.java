package com.example.news_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrDto {
    private String fullName;
    private String email;
    private String password;
    private String phone;
}
