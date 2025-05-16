package com.pessoais.domain.model.generic;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID) // Verifique se o Hibernate 6+ está sendo usado
	private String id;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BaseEntity)) return false;
		BaseEntity that = (BaseEntity) o;
		return id != null && id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return 31;
	}
}
