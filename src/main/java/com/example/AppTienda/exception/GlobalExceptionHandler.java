package com.example.AppTienda.exception;

import java.util.HashMap;
import java.util.Map;

import com.example.AppTienda.dto.HttpGlobalResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<HttpGlobalResponseDTO<?>> handleCustomException(CustomException ex) {
        HttpGlobalResponseDTO<?> response = new HttpGlobalResponseDTO<>(ex.getMessage(), null);
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpGlobalResponseDTO<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );

        HttpGlobalResponseDTO<?> response = new HttpGlobalResponseDTO<>("Error de validación en los datos enviados", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpGlobalResponseDTO<?>> handleGenericException(Exception ex) {
        HttpGlobalResponseDTO<?> response = new HttpGlobalResponseDTO<>("Error interno del servidor: " + ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}