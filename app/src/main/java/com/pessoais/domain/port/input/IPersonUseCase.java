package com.pessoais.domain.port.input;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.pessoais.adapter.input.rest.dto.PersonDTO;

import java.util.Map;

public interface IPersonUseCase {

    PersonDTO create(PersonDTO personDTO);

    PersonDTO getPerson(String idPerson);

    void delete(String idPerson);

    boolean searchPerson(String idPerson);

    PersonDTO patchPerson(String idPerson, JsonPatch patch) throws JsonPatchException, JsonProcessingException;
}
