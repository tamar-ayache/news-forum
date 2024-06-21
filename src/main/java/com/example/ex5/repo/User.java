//package com.example.ex5.repo;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
//
//@Entity
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(unique = true, nullable = false)
////    @NotEmpty(message = "Username is mandatory")
////    @Size(max = 20, message = "Username cannot exceed 20 characters")
//    private String username;
//
////    @Size(max = 50, message = "Password cannot exceed 50 characters")
////    @Column(name = "password", length = 50)
//    private String password;
//
//    private String role;
//    // גם אם אתה רוצה להוסיף את הגבלת הכניסה של הסיסמה כאן
//    // בנוסף, אם אתה רוצה, תוכל להוסיף עוד עמודות למשתמש ולפעולות עם GETTERS and SETTERS
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//}
