package com.example.sights.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

@SuppressWarnings("resource")
@Configuration
public class TestContainersConfig {

    private static final PostgreSQLContainer<?> postgres;

    static {
        postgres = new PostgreSQLContainer<>("postgres:16.4")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        postgres.start();
    }

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return postgres;
    }
}
