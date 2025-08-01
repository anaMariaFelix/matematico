package com.anamariafelix.matematico.mapper;

import com.anamariafelix.matematico.dto.PersonDTO;
import com.anamariafelix.matematico.model.Person;

public class ObjectMapper {

    public static PersonDTO toDTO(Person person) {
        if (person == null) {
            return null;
        }
        PersonDTO dto = new PersonDTO();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setAddress(person.getAddress());
        dto.setGender(person.getGender());
        dto.setEnabled(true); // valor padrão
        return dto;
    }

    public static Person toEntity(PersonDTO dto) {
        if (dto == null) {
            return null;
        }
        Person person = new Person();
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setAddress(dto.getAddress());
        person.setGender(dto.getGender());
        return person;
    }

}
