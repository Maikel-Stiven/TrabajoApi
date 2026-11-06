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
        HttpGlobalResponseDTO<Object> response = HttpGlobalResponseDTO.<Object>builder()
                .message(ex.getMessage())
                .data(null)
                .build();
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpGlobalResponseDTO>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );

        HttpGlobalResponseDTO> response = HttpGlobalResponseDTO.>builder()
                .message("Error de validación en los datos enviados")
                .data(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpGlobalResponseDTO<Object>> handleGenericException(Exception ex) {
        HttpGlobalResponseDTO<Object> response = HttpGlobalResponseDTO.<Object>builder()
                .message("Error interno del servidor: " + ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}