package com.example.parcial.excepciones;

public class MontoInvalidoException extends RuntimeException {
    public MontoInvalidoException(String message) {
        super(message);
    }
}