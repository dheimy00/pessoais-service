package com.pessoais.adapter.input.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.pessoais.adapter.input.rest.dto.PersonDTO;
import com.pessoais.domain.port.input.IPersonUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/persons")
@RequiredArgsConstructor
public class PersonController {

    private final IPersonUseCase personUseCase;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PersonDTO person) {
        return ResponseEntity.ok(personUseCase.create(person));
    }

    @GetMapping("/{id_person}")
    public ResponseEntity<?> findByIdUser(@PathVariable("id_person") String idPerson) {
        return ResponseEntity.ok(personUseCase.getPerson(idPerson));
    }

    @GetMapping("/searchCustomer/{id_person}")
    public ResponseEntity<?> searchPerson(@PathVariable("id_person") String idPerson) {
        return ResponseEntity.ok(personUseCase.searchPerson(idPerson));
    }

    @PatchMapping(value = "/{id_person}/updated",consumes = "application/json-patch+json")
    public ResponseEntity<?> patchProduct(@PathVariable("id_person") String idPerson, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        personUseCase.patchPerson(idPerson, patch);
        return ResponseEntity.ok("Data Person updated successfully");
    }
}
