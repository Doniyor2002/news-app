package com.example.news_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsDto {
    @NotNull(message = "Name kiritish majburiy")
    private String name;
    @NotNull(message = "Category tanlash shart")
    private Long category_id;
}
