package com.pessoais.adapter.input.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class ContactDTO {

    @JsonProperty("id_contact")
    private String idContact;

    @JsonProperty("telephone")
    @NotBlank(message = "O campo telefone é obrigatório")
    private String telephone;

    @JsonProperty("cellPhone")
    @NotBlank(message = "O campo celular é obrigatório")
    private String cellPhone;

    @JsonProperty("email")
    @NotBlank(message = "O campo e-mail é obrigatório")
    @Email(message = "O formato do e-mail é inválido")
    private String email;
}

