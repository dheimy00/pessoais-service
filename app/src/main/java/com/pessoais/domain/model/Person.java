package com.pessoais.domain.model;

import com.pessoais.domain.model.generic.BaseEntityAudit;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "persons")
public class Person extends BaseEntityAudit {

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "cpf",nullable = false)
    private String cpf;

    @Column(name = "birth",nullable = false)
    private LocalDate birth;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;
}
