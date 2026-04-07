package com.example.parcial.excepciones;

public class PeleadorNoEncontradoException extends NotFoundException {
    public PeleadorNoEncontradoException(String message) {
        super(message);
    }
}