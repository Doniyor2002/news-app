package com.example.news_app.entity;

import com.example.news_app.entity.template.AbsNameEntity;
import lombok.*;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Attachment extends AbsNameEntity {
    private String contentType;//.xlsx .docx
    private Long size;
    private String url;
}
