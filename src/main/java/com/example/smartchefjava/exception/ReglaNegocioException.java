package com.example.smartchefjava.exception;

// Una regla del cliente impide la operación → 422
public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
