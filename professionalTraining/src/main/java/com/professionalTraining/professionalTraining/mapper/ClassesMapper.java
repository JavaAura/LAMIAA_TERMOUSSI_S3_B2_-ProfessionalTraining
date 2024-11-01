package com.professionalTraining.professionalTraining.mapper;

import com.professionalTraining.professionalTraining.dto.ClassesDTO;
import com.professionalTraining.professionalTraining.entities.Classes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClassesMapper {
    ClassesMapper INSTANCE = Mappers.getMapper(ClassesMapper.class);
    Classes toEntity(ClassesDTO classesDTO);

    ClassesDTO toDTO(Classes classes);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ClassesDTO dto, @MappingTarget Classes entity);
}
