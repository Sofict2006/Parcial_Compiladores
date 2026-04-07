package com.example.parcial.excepciones;

public class UsuarioNoEncontradoException extends NotFoundException {
    public UsuarioNoEncontradoException(String message) {
        super(message);
    }
}