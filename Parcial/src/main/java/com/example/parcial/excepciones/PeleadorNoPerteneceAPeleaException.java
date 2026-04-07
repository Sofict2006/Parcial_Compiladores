package com.example.parcial.excepciones;

public class PeleadorNoPerteneceAPeleaException extends RuntimeException {
    public PeleadorNoPerteneceAPeleaException(String message) {
        super(message);
    }
}
