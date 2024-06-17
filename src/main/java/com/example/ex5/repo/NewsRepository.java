package com.example.ex5.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByTitle(String title);
    List<News> findTitleByContent(String content);
    List<News> findByContent(String content);
    List<News> findByTitleAndContent(String title, String content);
}
