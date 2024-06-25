package com.example.ex5.services;

import com.example.ex5.repo.News;
import com.example.ex5.repo.NewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Service for managing news articles.
 */
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    /**
     * Finds news articles with duplicate titles.
     *
     * @param title the title to search for duplicates
     * @return a list of news articles with the specified title
     */
    public List<News> findDuplicateTitle(String title) {
        List<News> Newsp = newsRepository.findByTitle(title);
        return Newsp;
    }
}
