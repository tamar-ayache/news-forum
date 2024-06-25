package com.example.ex5.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing News entities.
 */
public interface NewsRepository extends JpaRepository<News, Long> {

    /**
     * Finds news articles by their title.
     *
     * @param title the title of the news articles to find
     * @return a list of news articles with the specified title
     */
    List<News> findByTitle(String title);

    /**
     * Finds news articles by their content.
     *
     * @param content the content of the news articles to find
     * @return a list of news articles with the specified content
     */
    List<News> findTitleByContent(String content);

    /**
     * Finds news articles by their content.
     *
     * @param content the content of the news articles to find
     * @return a list of news articles with the specified content
     */
    List<News> findByContent(String content);

    /**
     * Finds news articles by their title and content.
     *
     * @param title the title of the news articles to find
     * @param content the content of the news articles to find
     * @return a list of news articles with the specified title and content
     */
    List<News> findByTitleAndContent(String title, String content);
}
