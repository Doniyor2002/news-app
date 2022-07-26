package com.example.news_app.repository;

import com.example.news_app.entity.Comment;
import com.example.news_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentResository extends JpaRepository<Comment, UUID> {
}
