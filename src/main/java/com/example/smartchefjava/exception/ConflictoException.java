package com.example.smartchefjava.exception;

// Lo que se quiere guardar choca con algo que ya existe → 409
public class ConflictoException extends RuntimeException {
    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}
