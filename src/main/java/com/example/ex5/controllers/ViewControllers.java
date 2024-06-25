package com.example.ex5.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for adding view controllers.
 */
@Configuration
public class ViewControllers implements WebMvcConfigurer {

    /**
     * Adds view controllers to the registry.
     *
     * @param registry the ViewControllerRegistry to configure
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }
}
