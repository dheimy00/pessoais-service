package com.pessoais.adapter.input.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    @JsonProperty("id_person")
    private String id;

    @JsonProperty("name")
    @NotBlank(message = "Name field is mandatory")
    private String name;

    @JsonProperty("cpf")
    @NotBlank(message = "CPF field is mandatory")
    private String cpf;

    @JsonProperty("birth")
    @NotBlank(message = "Birth field is mandatory")
    private String birth;

    @Valid
    @NotEmpty
    @JsonProperty("contacts")
    private List<ContactDTO> contacts;

    @Valid
    @NotEmpty
    @JsonProperty("addresses")
    private List<AddressDTO> addresses;
}
