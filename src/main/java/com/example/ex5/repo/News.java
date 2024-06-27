package com.example.ex5.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

/**
 * Entity class representing a news article.
 */
@Entity
public class News implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String title;

    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String content;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    /**
     * Gets the ID of the news article.
     *
     * @return the ID of the news article
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the news article.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the title of the news article.
     *
     * @return the title of the news article
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the news article.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the content of the news article.
     *
     * @return the content of the news article
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the news article.
     *
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the list of comments associated with the news article.
     *
     * @return the list of comments associated with the news article
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Sets the list of comments associated with the news article.
     *
     * @param comments the list of comments to set
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
