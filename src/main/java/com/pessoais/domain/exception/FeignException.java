package com.pessoais.domain.exception;

public class FeignException extends BusinessException {

    public FeignException(String errorCode, String message) {
        super(errorCode, message);
    }
}