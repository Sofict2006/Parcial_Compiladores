package com.example.parcial.excepciones;

public class PeleaNoEncontradaException extends NotFoundException {
    public PeleaNoEncontradaException(String message) {
        super(message);
    }
}