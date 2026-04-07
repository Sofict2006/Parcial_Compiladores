package com.example.parcial.excepciones;

public class CorreoDuplicadoException extends RuntimeException {
    public CorreoDuplicadoException(String message) {
        super(message);
    }
}