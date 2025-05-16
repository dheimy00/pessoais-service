package com.pessoais.application.usecase.mappers;

import com.pessoais.adapter.input.rest.dto.AddressDTO;
import com.pessoais.adapter.input.rest.dto.ContactDTO;
import com.pessoais.adapter.input.rest.dto.PersonDTO;
import com.pessoais.domain.model.Address;
import com.pessoais.domain.model.Contact;
import com.pessoais.domain.model.Person;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PersonMapper {


    @Mapping(target = "contacts", source = "contacts")
    @Mapping(target = "addresses", source = "addresses")
    @Mapping(target = "cpf", source = "cpf")
    Person toEntity(PersonDTO dto);

    @Mapping(target = "contacts", source = "contacts")
    @Mapping(target = "addresses", source = "addresses")
    @Mapping(target = "cpf", source = "cpf")
    PersonDTO toDto(Person entity);

    Contact toEntity(ContactDTO dto);

    ContactDTO toDTO(Contact entity);

    @Mapping(target = "zipCode", source = "zipCode")
    Address toEntity(AddressDTO dto);

    @Mapping(target = "zipCode", source = "zipCode")
    AddressDTO toDTO(Address entity);

    @IterableMapping(elementTargetType = Contact.class)
    List<Contact> toContactList(List<ContactDTO> dtos);

    @IterableMapping(elementTargetType = ContactDTO.class)
    List<ContactDTO> toContactDTOList(List<Contact> entities);

    @IterableMapping(elementTargetType = Address.class)
    List<Address> toAddressList(List<AddressDTO> dtos);

    @IterableMapping(elementTargetType = AddressDTO.class)
    List<AddressDTO> toAddressDTOList(List<Address> entities);
}
