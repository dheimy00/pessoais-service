package com.pessoais.adapter.output.persistence;

import com.pessoais.domain.model.Person;
import com.pessoais.domain.port.output.IPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class PersonRepository implements IPersonRepository {

    private final DataPersonRepository dataPersonRepository;

    @Override
    public Person save(Person user) {
        return dataPersonRepository.save(user);
    }

    @Override
    public Optional<Person> findByIdPerson(String id) {
        return dataPersonRepository.findById(id);
    }

    @Override
    public boolean existByCpf(String cpf) {
        return dataPersonRepository.existsByCpf(cpf);
    }

    @Override
    public void delete(String id) {
        dataPersonRepository.deleteById(id);
    }

}
