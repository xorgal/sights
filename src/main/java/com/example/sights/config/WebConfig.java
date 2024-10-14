package com.example.sights.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.example.sights.controller" })
public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(@SuppressWarnings("null") ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/swagger-ui.html**")
                                .addResourceLocations("classpath:/META-INF/resources/");

                registry.addResourceHandler("/webjars/**")
                                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
}