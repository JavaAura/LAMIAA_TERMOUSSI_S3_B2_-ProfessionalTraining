package com.professionalTraining.professionalTraining.mapper;

import com.professionalTraining.professionalTraining.dto.TrainingDTO;
import com.professionalTraining.professionalTraining.entities.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrainingMapper {
    TrainingMapper INSTANCE = Mappers.getMapper(TrainingMapper.class);

    Training toEntity(TrainingDTO trainingDTO);

    TrainingDTO toDTO(Training training);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(TrainingDTO dto, @MappingTarget Training entity);
}
