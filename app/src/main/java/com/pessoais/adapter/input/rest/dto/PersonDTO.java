package com.pessoais.adapter.input.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    @JsonProperty("id_person")
    private String idPerson;

    @JsonProperty("name")
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String name;

    @JsonProperty("cpf")
    @NotBlank(message = "O CPF é obrigatório")
    @CPF(message = "CPF inválido")
    private String cpf;

    @JsonProperty("birth")
    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve estar no passado")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birth;

    @Valid
    @NotEmpty(message = "A lista de contatos não pode estar vazia")
    @JsonProperty("contacts")
    private List<ContactDTO> contacts;

    @Valid
    @NotEmpty(message = "A lista de endereços não pode estar vazia")
    @JsonProperty("addresses")
    private List<AddressDTO> addresses;
}
