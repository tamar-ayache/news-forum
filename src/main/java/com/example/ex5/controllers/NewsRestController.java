package com.example.ex5.controllers;

import com.example.ex5.repo.News;
import com.example.ex5.repo.NewsRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class NewsRestController {
    @Autowired
    private NewsRepository repository;

    /** http://localhost:8080/api/addnews
     * In this rest endpoint we handle validation errors with a custom exception handler
     * using postman try sending the following post params:
     * { "title": "asdasd", "content": "asdasd"}
     * { "title": "", "content": "asdasd"}
     * @param news
     * @return
     */
    @PostMapping("/addnews")
    public News addNewsRest(@Valid @RequestBody News news) {
        repository.save(news);
        return news;
    }

    @GetMapping("/newsp")
    public Iterable<News> getNewsp() {
        return repository.findAll();
    }

    // then comment out the exception handler below and rerun the request,
    // you will see that the response is a 400 bad request returned by spring
    // in other words, if you don't handle the validation errors, Spring will return a 400 bad request
    // writing a custom eception handler allows us to return the errors in a more news friendly way
    // and also allows us to return any other Http response, even a 200 OK response instead of a 400 bad request

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}