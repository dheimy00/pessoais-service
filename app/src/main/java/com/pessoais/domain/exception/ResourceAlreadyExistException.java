package com.pessoais.domain.exception;

public class ResourceAlreadyExistException extends BusinessException {

    public ResourceAlreadyExistException(String errorCode, String message) {
        super(errorCode, message);
    }
}
