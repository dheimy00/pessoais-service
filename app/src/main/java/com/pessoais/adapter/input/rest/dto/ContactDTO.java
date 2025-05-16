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
    @NotBlank(message = "Telephone field is mandatory")
    private String telephone;

    @JsonProperty("cellPhone")
    @NotBlank(message = "Cell Phone field is mandatory")
    private String cellPhone;

    @JsonProperty("email")
    @NotBlank(message = "Email field is mandatory")
    @Email
    private String email;
}
