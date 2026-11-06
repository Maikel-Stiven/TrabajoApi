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
    public ResponseEntity<HttpGlobalResponseDTO<Object>> handleCustomException(CustomException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(HttpGlobalResponseDTO.of(ex.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpGlobalResponseDTO>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(HttpGlobalResponseDTO.of("Error de validación en los datos enviados", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpGlobalResponseDTO<Object>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpGlobalResponseDTO.of("Error interno del servidor: " + ex.getMessage(), null));
    }
}