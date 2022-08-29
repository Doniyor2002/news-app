package com.example.news_app.repository;

import com.example.news_app.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findAllByNameContainingIgnoreCaseAndCreatedate(String name, Date date,Pageable pageable);
    Page<Comment> findAllByUser_Phone(String phone,Pageable pageable);

}
