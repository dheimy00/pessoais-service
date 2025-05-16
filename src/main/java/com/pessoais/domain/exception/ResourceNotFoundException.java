package com.pessoais.domain.exception;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String errorCode, String message) {
        super(errorCode, message);
    }
}
