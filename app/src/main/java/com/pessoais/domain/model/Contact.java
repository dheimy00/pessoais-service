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
@Table(name = "contacts")
public class Contact extends BaseEntityAudit {

    @Column(name = "telephone",nullable = false)
    private String telephone;

    @Column(name = "cell_phone",nullable = false)
    private String cellPhone;

    @Column(name = "email",nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
}
