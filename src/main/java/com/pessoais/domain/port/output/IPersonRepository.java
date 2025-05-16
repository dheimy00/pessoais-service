package com.pessoais.domain.port.output;

import com.pessoais.domain.model.Person;

import java.util.Optional;

public interface IPersonRepository {

    Person save(Person user);
    Optional<Person> findByIdPerson(String id);

    boolean existByCpf(String cpf);

    void delete(String id);

}
