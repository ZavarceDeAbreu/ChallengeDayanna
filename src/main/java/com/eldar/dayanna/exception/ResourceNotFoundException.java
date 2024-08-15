package com.eldar.dayanna.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String id) {
        super("Recurso con ID " + id + " no encontrada.");
    }
}

