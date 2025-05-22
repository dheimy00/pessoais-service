package com.pessoais.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.pessoais.adapter.input.rest.dto.PersonDTO;
import com.pessoais.application.usecase.mappers.PersonMapper;
import com.pessoais.domain.exception.ResourceAlreadyExistException;
import com.pessoais.domain.exception.ResourceNotFoundException;
import com.pessoais.domain.model.Person;
import com.pessoais.domain.port.input.IPersonUseCase;
import com.pessoais.domain.port.output.IPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PersonUseCase implements IPersonUseCase {

    private final IPersonRepository personRepository;
    private final PersonMapper personMapper;
    private final ObjectMapper objectMapper;

    @Override
    public PersonDTO create(PersonDTO personDTO) {
        log.info("Iniciando criação da pessoa com CPF: {}", personDTO.getCpf());

        if (personRepository.existByCpf(personDTO.getCpf())) {
            log.info("Pessoa com CPF {} já existe", personDTO.getCpf());
            throw new ResourceAlreadyExistException("ALREADY_EXIST", "Pessoa já cadastrada com CPF: " + personDTO.getCpf());
        }

        var person = personMapper.toEntity(personDTO);

        if (person.getContacts() != null) {
            person.getContacts().forEach(contact -> contact.setPerson(person));
            log.debug("Contatos associados à pessoa.");
        }

        if (person.getAddresses() != null) {
            person.getAddresses().forEach(address -> address.setPerson(person));
            log.debug("Endereços associados à pessoa.");
        }

        var saved = personRepository.save(person);
        log.info("Pessoa criada com sucesso. ID: {}", saved.getId());

        return personMapper.toDto(saved);
    }

    @Override
    public PersonDTO getPerson(String idPerson) {
        log.info("Buscando pessoa com ID: {}", idPerson);

        var person = personRepository.findByIdPerson(idPerson)
                .orElseThrow(() -> {
                    log.info("Pessoa não encontrada com o ID: {}", idPerson);
                    return new ResourceNotFoundException("Pessoa não encontrada", "Pessoa não encontrada com ID: " + idPerson);
                });

        log.info("Pessoa encontrada com ID: {}", idPerson);
        return personMapper.toDto(person);
    }

    @Override
    public boolean searchPerson(String idPerson) {
        log.info("Buscando pessoa com ID: {}", idPerson);

        personRepository.findByIdPerson(idPerson)
                .orElseThrow(() -> {
                    log.info("Pessoa não encontrada com o ID: {}", idPerson);
                    return new ResourceNotFoundException("Pessoa não encontrada", "Pessoa não encontrada com ID: " + idPerson);
                });

        log.info("Pessoa encontrada com ID: {}", idPerson);
        return true;
    }

    @Override
    public void delete(String id) {
        log.info("Método de exclusão ainda não implementado. ID recebido: {}", id);
        // Lógica de exclusão poderá ser implementada aqui
    }

    @Override
    public PersonDTO patchPerson(String idPerson, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        log.info("Aplicando patch na pessoa com ID: {}", idPerson);

        var person = personRepository.findByIdPerson(idPerson)
                .orElseThrow(() -> {
                    log.info("Pessoa não encontrada para patch com ID: {}", idPerson);
                    return new ResourceNotFoundException("Pessoa não encontrada", "Pessoa não encontrada com ID: " + idPerson);
                });

        person = applyPatchToCustomer(patch, person);

        var saved = personRepository.save(person);
        log.info("Patch aplicado com sucesso na pessoa com ID: {}", saved.getId());

        return personMapper.toDto(saved);
    }

    private Person applyPatchToCustomer(JsonPatch patch, Person targetCustomer) throws JsonPatchException, JsonProcessingException {
        log.debug("Convertendo pessoa para JSON e aplicando patch.");
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, Person.class);
    }
}
