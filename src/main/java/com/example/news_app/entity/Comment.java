package com.example.news_app.entity;

import com.example.news_app.entity.template.AbsEntity;
import lombok.*;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Comment extends AbsEntity {
    private String name;
    @ManyToOne
    private News news;
    @ManyToOne
    private User user;
}
