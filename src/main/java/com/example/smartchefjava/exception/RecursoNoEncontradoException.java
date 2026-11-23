package com.example.smartchefjava.exception;

// Algo que se busca no existe → 404
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
