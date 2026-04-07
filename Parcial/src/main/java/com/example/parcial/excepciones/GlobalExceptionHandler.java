package com.example.parcial.excepciones;

import tools.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex, HttpServletRequest req) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(Instant.now());
        apiError.setStatus(HttpStatus.NOT_FOUND.value());
        apiError.setError(ex.getMessage());
        apiError.setPath(req.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(CorreoDuplicadoException.class)
    public ResponseEntity<ApiError> handleCorreoDuplicadoException(CorreoDuplicadoException ex,
            HttpServletRequest req) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(Instant.now());
        apiError.setStatus(HttpStatus.CONFLICT.value());
        apiError.setError(ex.getMessage());
        apiError.setPath(req.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler({
            MontoInvalidoException.class,
            PeleadoresRepetidosException.class,
            PeleadorNoPerteneceAPeleaException.class
    })
    public ResponseEntity<ApiError> handleBadRequestExceptions(RuntimeException ex, HttpServletRequest req) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(Instant.now());
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setError(ex.getMessage());
        apiError.setPath(req.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest req) {
        ApiError apiError = new ApiError();
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        apiError.setTimestamp(Instant.now());
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setError("Error de validación");
        apiError.setPath(req.getRequestURI());
        apiError.setFieldsErrors(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex,
            HttpServletRequest req) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(Instant.now());
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setPath(req.getRequestURI());

        String mensaje = "El cuerpo de la petición tiene un formato inválido";

        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            String campo = invalidFormatException.getPath().stream()
                    .findFirst()
                    .map(ref -> ref.toString())
                    .orElse("");

            if (campo.contains("monto")) {
                mensaje = "El monto debe ser numérico";
            }
        }

        apiError.setError(mensaje);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            HttpServletRequest req) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(Instant.now());
        apiError.setStatus(HttpStatus.CONFLICT.value());
        apiError.setError("Ya existe un registro con esos datos únicos");
        apiError.setPath(req.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }
}