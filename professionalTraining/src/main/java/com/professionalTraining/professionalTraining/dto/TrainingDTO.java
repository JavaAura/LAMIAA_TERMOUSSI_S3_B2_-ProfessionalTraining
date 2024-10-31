package com.professionalTraining.professionalTraining.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDTO {
    private Long id;

    private String title;

    private String level;

    private String prerequisites;

    private Integer minCapacity;

    private Integer maxCapacity;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;
}
