package com.example.sights.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Schema(description = "Exception: Resource not found")
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
