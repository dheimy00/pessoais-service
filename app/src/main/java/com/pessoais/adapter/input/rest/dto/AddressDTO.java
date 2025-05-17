package com.pessoais.adapter.input.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class AddressDTO {

    @JsonProperty("id_address")
    private String idAddress;

    @JsonProperty("street")
    @NotBlank(message = "O campo rua é obrigatório")
    private String street;

    @JsonProperty("number")
    @NotNull(message = "O campo número é obrigatório")
    @Positive(message = "O número deve ser positivo")
    private int number;

    @JsonProperty("complement")
    @NotBlank(message = "O campo complemento é obrigatório")
    private String complement;

    @JsonProperty("neighborhood")
    @NotBlank(message = "O campo bairro é obrigatório")
    private String neighborhood;

    @JsonProperty("city")
    @NotBlank(message = "O campo cidade é obrigatório")
    private String city;

    @JsonProperty("state")
    @NotBlank(message = "O campo estado é obrigatório")
    private String state;

    @JsonProperty("zip_code")
    @NotBlank(message = "O campo CEP é obrigatório")
    private String zipCode;
}
