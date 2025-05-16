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

    @JsonProperty("street")
    @NotBlank(message = "Street field is mandatory")
    private String street;

    @NotNull(message = "Number field is mandatory")
    @Positive
    @JsonProperty("number")
    private int number;

    @JsonProperty("complement")
    @NotBlank(message = "Complement field is mandatory")
    private String complement;

    @JsonProperty("neighborhood")
    @NotBlank(message = "Neighborhood field is mandatory")
    private String neighborhood;

    @JsonProperty("city")
    @NotBlank(message = "City field is mandatory")
    private String city;

    @JsonProperty("state")
    @NotBlank(message = "State field is mandatory")
    private String state;

    @JsonProperty("zip_code")
    @NotBlank(message = "Zip Code field is mandatory")
    private String zipCode;
}
