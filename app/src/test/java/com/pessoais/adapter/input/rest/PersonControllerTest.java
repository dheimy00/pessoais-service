package com.pessoais.adapter.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pessoais.adapter.input.rest.dto.AddressDTO;
import com.pessoais.adapter.input.rest.dto.ContactDTO;
import com.pessoais.adapter.input.rest.dto.PersonDTO;
import com.pessoais.domain.port.input.IPersonUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PersonControllerTest {

    private MockMvc mockMvc;
    private IPersonUseCase personUseCase;
    private ObjectMapper objectMapper;

    private PersonDTO mockPerson;

    @BeforeEach
    void setup() {
        personUseCase = mock(IPersonUseCase.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(personUseCase)).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Adiciona suporte para LocalDate
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockPerson = PersonDTO.builder()
                .idPerson("1")
                .name("João da Silva")
                .cpf("12345678909") // CPF válido sintaticamente
                .birth(LocalDate.of(1990, 1, 1))
                .contacts(List.of(ContactDTO.builder()
                        .cellPhone("11999999999")
                        .telephone("1133334444")
                        .email("joao@email.com")
                        .build()))
                .addresses(List.of(AddressDTO.builder()
                        .street("Rua das Flores")
                        .number(123)
                        .complement("Apto 101")
                        .neighborhood("Centro")
                        .city("São Paulo")
                        .state("SP")
                        .zipCode("01000-000")
                        .build()))
                .build();
    }

    @Test
    void shouldCreatePersonSuccessfully() throws Exception {
        when(personUseCase.create(any())).thenReturn(mockPerson);

        mockMvc.perform(post("/v1/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_person").value("1"));

        verify(personUseCase).create(any());
    }

    @Test
    void shouldReturnPersonById() throws Exception {
        when(personUseCase.getPerson("1")).thenReturn(mockPerson);

        mockMvc.perform(get("/v1/persons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_person").value("1"));

        verify(personUseCase).getPerson("1");
    }

    @Test
    void shouldSearchPersonSuccessfully() throws Exception {
        when(personUseCase.searchPerson("1")).thenReturn(true);

        mockMvc.perform(get("/v1/persons/searchCustomer/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(personUseCase).searchPerson("1");
    }
}
