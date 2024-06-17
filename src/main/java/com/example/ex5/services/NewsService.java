package com.example.ex5.services;

import com.example.ex5.repo.News;
import com.example.ex5.repo.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public List<News> findDuplicateTitle(String title) {
        List<News> Newsp = newsRepository.findByTitle(title);
        return Newsp;
    }
}
