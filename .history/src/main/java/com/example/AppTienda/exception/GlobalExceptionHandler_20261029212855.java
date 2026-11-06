package com.example.AppTienda.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.AppTienda.dto.HttpGlobalResponseDTO;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    
    @ExceptionHandler (CustomException.class)
    public ResponseEntity<HttpGlobalResponseDTO<Object>> handleCustomException(CustomException ex){
        return ResponseEntity.status(ex.getStatus())
        .body(HttpGlobalResponseDTO.builder()
        .message(ex.getMessage())
        .data(null)
        .build());
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<HttpGlobalResponseDTO>>

}
