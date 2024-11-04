package com.professionalTraining.professionalTraining.mapper;

import com.professionalTraining.professionalTraining.dto.LearnerDTO;
import com.professionalTraining.professionalTraining.entities.Learner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface LearnerMapper {

    @Mapping(target = "assignedClass.id", source = "classId")
    @Mapping(target = "training.id", source = "trainingId")
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    Learner toEntity(LearnerDTO learnerDTO);

    @Mapping(target = "classId", source = "assignedClass.id")
    @Mapping(target = "trainingId", source = "training.id")
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    LearnerDTO toDTO(Learner learner);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedClass.id", source = "classId")
    @Mapping(target = "training.id", source = "trainingId")
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    void updateEntityFromDTO(LearnerDTO dto, @MappingTarget Learner entity);
}
