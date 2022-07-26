package com.example.news_app.entity;

import com.example.news_app.entity.template.AbsEntity;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class News extends AbsEntity {
    private String name;
    private String picture;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Category category;
    @ManyToOne
    private User user;
    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE,CascadeType.MERGE},fetch = FetchType.EAGER,mappedBy = "news")
    private List<Comment> comment;
}
