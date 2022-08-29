package com.example.news_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @NotNull(message = "UserName kiritish majburiy")
    private String userName;
    private String fullName;
    private Double salary;
    @NotNull(message = "Role tanlash shart")
    private List<Long> roleId;
    @NotNull(message = "password kiritish shart")
    private String password;

}
