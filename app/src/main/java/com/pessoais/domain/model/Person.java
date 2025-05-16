package com.pessoais.domain.model;

import com.pessoais.domain.model.generic.BaseEntityAudit;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
public class Person extends BaseEntityAudit {

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "cpf",nullable = false)
    private String cpf;

    @Column(name = "birth",nullable = false)
    private String birth;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;
}
