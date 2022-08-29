package com.example.news_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResCommentDto {
    private String name;
    private String newsName;
    private String userName;
    private String createDate;

}
