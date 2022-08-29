package com.example.news_app.repository;

import com.example.news_app.entity.News;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
    Page<News> findAllByNameContainingIgnoreCaseAndCategory_NameContainingIgnoreCaseAndCreatedate(String name, String categoryName,Date date, Pageable pageable);

    Page<News> findAllByUser_Phone(String phone,Pageable pageable);
}
