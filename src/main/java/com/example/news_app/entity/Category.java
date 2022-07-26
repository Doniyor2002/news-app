package com.example.news_app.entity;

import com.example.news_app.entity.template.AbsNameEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category extends AbsNameEntity {
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<News> news;
}
