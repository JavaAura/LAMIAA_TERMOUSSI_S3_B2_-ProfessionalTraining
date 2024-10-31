package com.professionalTraining.professionalTraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearnerDTO extends PersonDTO{
    private String level;
    private Long classId;
}
