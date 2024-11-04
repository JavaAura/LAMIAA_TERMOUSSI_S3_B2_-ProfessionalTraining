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

    @Mapping(target = "instructor.id", source = "instructorId")
    Classes toEntity(ClassesDTO classesDTO);

    @Mapping(target = "instructorId", source = "instructor.id")
    ClassesDTO toDTO(Classes classes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instructor.id", source = "instructorId")
    void updateEntityFromDTO(ClassesDTO dto, @MappingTarget Classes entity);
}
