package com.example.smartchefjava.exception;

// La petición no tiene sentido aunque el formato sea correcto → 400
public class PeticionIncorrectaException extends RuntimeException {
    public PeticionIncorrectaException(String mensaje) {
        super(mensaje);
    }
}
