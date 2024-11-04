package com.professionalTraining.professionalTraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDTO extends PersonDTO {
    private String speciality;


    private Long trainingId;
}
