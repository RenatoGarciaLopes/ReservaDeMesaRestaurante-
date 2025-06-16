package com.example.demo.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.service.Utils.ApiResponse;
import com.example.demo.service.Utils.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException er) {
        ErrorResponse error = new ErrorResponse("Recurso não encontrado", er.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(error));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException er) {
        List<DataErrors> erros = er.getFieldErrors().stream()
                .map(DataErrors::new)
                .toList();

        ApiResponse<List<DataErrors>> response = new ApiResponse<>(erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException er) {
        ErrorResponse error = new ErrorResponse("Operação inválida", er.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(error));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException er) {
        ErrorResponse error = new ErrorResponse("Argumento inválido", er.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(error));
    }

    public record DataErrors(String field, String message) {

        public DataErrors(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
