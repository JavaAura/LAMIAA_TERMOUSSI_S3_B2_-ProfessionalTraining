package com.professionalTraining.professionalTraining.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassesDTO {

    private Long id;
    private String name;
    private Integer roomNum;

    private Long instructorId;
}
