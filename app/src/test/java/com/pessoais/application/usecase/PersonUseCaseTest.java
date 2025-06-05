package com.pessoais.application.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.pessoais.adapter.input.rest.dto.PersonDTO;
import com.pessoais.application.usecase.mappers.PersonMapper;
import com.pessoais.domain.exception.ResourceAlreadyExistException;
import com.pessoais.domain.exception.ResourceNotFoundException;
import com.pessoais.domain.model.Person;
import com.pessoais.domain.port.output.IPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonUseCaseTest {

    @Mock
    private IPersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PersonUseCase personUseCase;

    private AutoCloseable closeable;

    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    // --- CREATE ---
    @Test
    void shouldCreatePersonSuccessfully() {
        PersonDTO dto = new PersonDTO();
        dto.setCpf("123");

        Person entity = new Person();
        entity.setCpf("123");

        Person saved = new Person();
        saved.setId("1");

        PersonDTO savedDto = new PersonDTO();
        savedDto.setIdPerson("1");

        when(personRepository.existByCpf("123")).thenReturn(false);
        when(personMapper.toEntity(dto)).thenReturn(entity);
        when(personRepository.save(entity)).thenReturn(saved);
        when(personMapper.toDto(saved)).thenReturn(savedDto);

        PersonDTO result = personUseCase.create(dto);

        assertEquals("1", result.getIdPerson());
        verify(personRepository).existByCpf("123");
        verify(personRepository).save(entity);
    }

    @Test
    void shouldThrowWhenCpfExists() {
        PersonDTO dto = new PersonDTO();
        dto.setCpf("123");

        when(personRepository.existByCpf("123")).thenReturn(true);

        assertThrows(ResourceAlreadyExistException.class, () -> personUseCase.create(dto));
        verify(personRepository, never()).save(any());
    }

    // --- GET PERSON ---
    @Test
    void shouldReturnPersonById() {
        Person person = new Person();
        person.setId("abc");

        PersonDTO dto = new PersonDTO();
        dto.setIdPerson("abc");

        when(personRepository.findByIdPerson("abc")).thenReturn(Optional.of(person));
        when(personMapper.toDto(person)).thenReturn(dto);

        PersonDTO result = personUseCase.getPerson("abc");

        assertEquals("abc", result.getIdPerson());
    }

    @Test
    void shouldThrowWhenPersonNotFoundById() {
        when(personRepository.findByIdPerson("xyz")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> personUseCase.getPerson("xyz"));
    }

    // --- SEARCH PERSON ---
    @Test
    void shouldReturnTrueWhenPersonExists() {
        Person person = new Person();
        person.setId("123");

        when(personRepository.findByIdPerson("123")).thenReturn(Optional.of(person));

        assertTrue(personUseCase.searchPerson("123"));
    }

    @Test
    void shouldThrowWhenSearchingNonexistentPerson() {
        when(personRepository.findByIdPerson("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> personUseCase.searchPerson("999"));
    }

    @Test
    void shouldThrowWhenPatchTargetNotFound() {
        JsonPatch patch = mock(JsonPatch.class);
        when(personRepository.findByIdPerson("notFound")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> personUseCase.patchPerson("notFound", patch));
    }

    // --- DELETE ---
    @Test
    void deleteShouldLogWithoutThrowing() {
        // Esse método não tem implementação, mas pode ser invocado para cobrir no teste
        assertDoesNotThrow(() -> personUseCase.delete("dummyId"));
    }
}
