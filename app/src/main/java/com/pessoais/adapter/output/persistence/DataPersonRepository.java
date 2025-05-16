package com.pessoais.adapter.output.persistence;

import com.pessoais.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPersonRepository extends JpaRepository<Person, String> {

    boolean existsByCpf(String cpf);
}
