package com.example.ex5.repo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity class representing a comment.
 */
@Entity
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Gets the ID of the comment.
     *
     * @return the ID of the comment
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the comment.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the content of the comment.
     *
     * @return the content of the comment
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the comment.
     *
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the news associated with the comment.
     *
     * @return the news associated with the comment
     */
    public News getNews() {
        return news;
    }

    /**
     * Sets the news associated with the comment.
     *
     * @param news the news to set
     */
    public void setNews(News news) {
        this.news = news;
    }

    /**
     * Gets the user who made the comment.
     *
     * @return the user who made the comment
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who made the comment.
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the creation date and time of the comment.
     *
     * @return the creation date and time of the comment
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date and time of the comment.
     *
     * @param createdAt the creation date and time to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the last update date and time of the comment.
     *
     * @return the last update date and time of the comment
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the last update date and time of the comment.
     *
     * @param updatedAt the last update date and time to set
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
