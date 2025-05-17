package com.pessoais.adapter.input.rest.exception;

import com.pessoais.adapter.input.rest.dto.ErrorResponse;
import com.pessoais.domain.exception.BusinessException;
import com.pessoais.domain.exception.FeignException;
import com.pessoais.domain.exception.ResourceAlreadyExistException;
import com.pessoais.domain.exception.ResourceBadRequestException;
import com.pessoais.domain.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(
            BusinessException ex, HttpServletRequest request) {
        log.error("Erro da aplicação: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {
        log.error("Recurso não encontrado: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleResourceBadRequestException(
            ResourceBadRequestException ex, HttpServletRequest request) {
        log.error("Requisição inválida para o recurso: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistException(
            ResourceAlreadyExistException ex, HttpServletRequest request) {
        log.error("Recurso já existente: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeignException(
            FeignException ex, HttpServletRequest request) {
        log.error("Erro ao chamar serviço externo (Feign): {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        log.error("Erro inesperado: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of("ERRO_INTERNO_SERVIDOR",
                        "Ocorreu um erro inesperado", request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        log.error("Erro de validação: {}", errors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("ERRO_VALIDACAO", "Falha de validação",
                        request.getRequestURI(), errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        log.error("Violação de restrição: {}", errors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("VIOLACAO_RESTRICAO", "Falha de validação",
                        request.getRequestURI(), errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidFormat(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Formato de dados inválido: " + ex.getMessage());
    }
}
