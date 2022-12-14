package com.example.news_app.dto;

import com.example.news_app.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResUserDto {
    private String fullName;
    private String phone;
    private Double salary;
    private Set<String> roles;
    private String password;


}
