package org.enascimento.api.exception;

import java.io.Serial;

public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4186561338570645697L;

    public NotFoundException(String message) {
        super(message);
    }
}