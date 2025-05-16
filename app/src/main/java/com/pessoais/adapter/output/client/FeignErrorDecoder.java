package com.pessoais.adapter.output.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pessoais.domain.exception.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String errorMessage = getErrorMessage(response);
        log.error("Feign error [{}] in {}: {}", response.status(), methodKey, errorMessage);

        return switch (response.status()) {
            case 400 -> new FeignException("INVALID_REQUEST", "Solicitação de serviço inválida: " + errorMessage);
            case 404 -> new FeignException("NOT_FOUND", "Não encontrado: " + errorMessage);
            case 500 -> new FeignException("USER_SERVICE_ERROR", "Erro de serviço: " + errorMessage);
            default -> new ResponseStatusException(HttpStatus.valueOf(response.status()), errorMessage);
        };
    }

    private String getErrorMessage(Response response) {
        try (InputStream bodyIs = response.body().asInputStream()) {
            String json = new String(bodyIs.readAllBytes(), StandardCharsets.UTF_8);

            try {
                ObjectMapper mapper = new ObjectMapper();
                RemoteError remoteError = mapper.readValue(json, RemoteError.class);
                return String.format("[%s] %s (%s)", remoteError.getErrorCode(), remoteError.getMessage(), remoteError.getPath());
            } catch (Exception e) {
                return json;
            }

        } catch (IOException e) {
            return "Erro ao ler mensagem de erro";
        }
    }
}