package org.enascimento.api.exception;

import java.io.Serial;

public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8120769285646720049L;

    public BusinessException(String message) {
        super(message);
    }
}