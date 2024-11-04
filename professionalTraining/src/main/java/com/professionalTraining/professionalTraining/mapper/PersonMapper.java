package com.professionalTraining.professionalTraining.mapper;

import com.professionalTraining.professionalTraining.dto.PersonDTO;
import com.professionalTraining.professionalTraining.entities.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonMapper {
//    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    Person toEntity(PersonDTO personDTO);

    PersonDTO toDTO(Person person);
}
