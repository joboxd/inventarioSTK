package com.jacob.inventario.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jacob.inventario.utils.CustomExcepcion;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomExcepcion.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomExcepcion ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("errorCode", ex.getHttpStatus().value());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof ResponseStatusException rsEx) {
            status = (HttpStatus) rsEx.getStatusCode();
        }

        response.put("errorCode", status.value());
        response.put("message", ex.getMessage());
        
        return new ResponseEntity<>(response, status);
    }
}