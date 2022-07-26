package com.example.news_app.dto;

import com.example.news_app.entity.Comment;
import com.example.news_app.entity.News;
import com.example.news_app.entity.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResUserDto {
    private String fullName;
    private String phone;
    private Double salary;
    private Set<Roles> roleName;
    private String password;


}
