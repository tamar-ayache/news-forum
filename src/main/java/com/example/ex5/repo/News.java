package com.example.ex5.repo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.*;
import java.io.Serializable;

@Entity
public class News implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Title is mandatory")
    private String title;

    @NotEmpty(message = "content is mandatory")
    private String content;



    public News() {}

    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        // Annotation may not be enough
        // you can also perform your own validation inside setters
        // exception should be caught by service/controller
        if (title.length() > 32)
            throw new IllegalArgumentException("Title cannot exceed 32 characters");
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() { return content; }


    @Override
    public String toString() {
        return "News{" + "id=" + id + ", title=" + title + ", content=" + content + '}';
    }
}