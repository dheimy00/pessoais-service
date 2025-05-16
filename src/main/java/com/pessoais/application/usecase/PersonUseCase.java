package com.pessoais.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.pessoais.adapter.input.rest.dto.PersonDTO;
import com.pessoais.application.usecase.mappers.PersonMapper;
import com.pessoais.domain.exception.ResourceNotFoundException;
import com.pessoais.domain.model.Person;
import com.pessoais.domain.port.input.IPersonUseCase;
import com.pessoais.domain.port.output.IPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonUseCase implements IPersonUseCase {
    private final IPersonRepository personRepository;
    private final PersonMapper personMapper;
    private final ObjectMapper objectMapper;

    @Override
    public PersonDTO create(PersonDTO personDTO) {
        log.info("Starting creation of person with CPF: {}", personDTO.getCpf());

        if (personRepository.existByCpf(personDTO.getCpf())) {
            log.warn("Person with CPF {} already exists", personDTO.getCpf());
            throw new ResourceNotFoundException("Person already exists", "Person already exists with CPF: " + personDTO.getCpf());
        }

        var person = personMapper.toEntity(personDTO);

        if (person.getContacts() != null) {
            person.getContacts().forEach(contact -> contact.setPerson(person));
            log.debug("Contacts associated with the person.");
        }

        if (person.getAddresses() != null) {
            person.getAddresses().forEach(address -> address.setPerson(person));
            log.debug("Addresses associated with the person.");
        }

        var saved = personRepository.save(person);
        log.info("Person created successfully. ID: {}", saved.getId());

        return personMapper.toDto(saved);
    }

    @Override
    public PersonDTO getPerson(String idPerson) {
        log.info("Searching for person with ID: {}", idPerson);

        var person = personRepository.findByIdPerson(idPerson)
                .orElseThrow(() -> {
                    log.warn("Person not found with ID: {}", idPerson);
                    return new ResourceNotFoundException("Person not found", "Person not found with ID: " + idPerson);
                });

        log.info("Person found with ID: {}", idPerson);
        return personMapper.toDto(person);
    }

    @Override
    public void delete(String id) {
        log.info("Delete method not yet implemented. Received ID: {}", id);
        // Implement logic here, if necessary
    }

    @Override
    public PersonDTO patchPerson(String idPerson, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        log.info("Applying patch to person with ID: {}", idPerson);

        var person = personRepository.findByIdPerson(idPerson)
                .orElseThrow(() -> {
                    log.warn("Person not found for patch with ID: {}", idPerson);
                    return new ResourceNotFoundException("Person not found", "Person not found with ID: " + idPerson);
                });

        person = applyPatchToCustomer(patch, person);

        var saved = personRepository.save(person);
        log.info("Patch successfully applied to person with ID: {}", saved.getId());

        return personMapper.toDto(saved);
    }

    private Person applyPatchToCustomer(JsonPatch patch, Person targetCustomer) throws JsonPatchException, JsonProcessingException {
        log.debug("Converting person to JSON and applying patch.");
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, Person.class);
    }
}
