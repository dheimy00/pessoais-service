package com.pessoais.domain.exception;

public class ResourceBadRequestException extends BusinessException {

    public ResourceBadRequestException(String errorCode, String message) {
        super(errorCode, message);
    }
}
