package com.professionalTraining.professionalTraining.mapper;

import com.professionalTraining.professionalTraining.dto.InstructorDTO;
import com.professionalTraining.professionalTraining.entities.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface InstructorMapper {

    InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);

    @Mapping(target = "training.id", source = "trainingId")
    Instructor toEntity(InstructorDTO instructorDTO);

    @Mapping(target = "trainingId", source = "training.id")
    InstructorDTO toDTO(Instructor instructor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "training.id", source = "trainingId")
    void updateEntityFromDTO(InstructorDTO dto, @MappingTarget Instructor entity);

}
