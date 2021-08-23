package com.compassouol.productms.commons.exceptions;

public class ResourceNotFoundException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
