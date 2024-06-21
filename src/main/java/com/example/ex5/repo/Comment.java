//package com.example.ex5.repo;
//
//import jakarta.persistence.*;
//import java.io.Serializable;
//
//@Entity
//public class Comment implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String content;
//
//    @ManyToOne
//    @JoinColumn(name = "news_id", nullable = false)
//    private News news;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public News getNews() {
//        return news;
//    }
//
//    public void setNews(News news) {
//        this.news = news;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//}
