package com.example.ex5.repo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
public class News implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Title is mandatory")
    @Size(max = 50, message = "Title cannot exceed 50 characters")
    private String title;

    @NotEmpty(message = "Content is mandatory")
    @Size(max = 255, message = "Content cannot exceed 255 characters")
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

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {

        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "News{" + "id=" + id + ", title='" + title + '\'' + ", content='" + content + '\'' + '}';
    }
}
