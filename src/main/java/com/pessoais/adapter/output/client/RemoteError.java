package com.pessoais.adapter.output.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoteError {
    private String errorCode;
    private String message;
    private String timestamp;
    private String path;
}
