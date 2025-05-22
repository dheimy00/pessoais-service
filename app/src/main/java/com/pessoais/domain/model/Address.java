package com.pessoais.domain.model;

import com.pessoais.domain.model.generic.BaseEntityAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class Address extends BaseEntityAudit {

    @Column(name = "street",nullable = false)
    private String street;

    @Column(name = "number",nullable = false)
    private int number;

    @Column(name = "complement",nullable = false)
    private String complement;

    @Column(name = "neighborhood",nullable = false)
    private String neighborhood;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "state",nullable = false)
    private String state;

    @Column(name = "zip_code",nullable = false)
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
}
