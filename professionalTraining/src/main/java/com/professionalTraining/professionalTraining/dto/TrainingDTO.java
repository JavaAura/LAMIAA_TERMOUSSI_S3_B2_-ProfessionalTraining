package com.professionalTraining.professionalTraining.dto;

import java.time.LocalDate;

import com.professionalTraining.professionalTraining.entities.enums.TrainingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Level is required")
    private String level;

    private String prerequisites;

    @Min(value = 1, message = "Minimum capacity must be at least 1")
    private Integer minCapacity;

    @Min(value = 1, message = "Maximum capacity must be at least 1")
    private Integer maxCapacity;

    @NotBlank(message = "Start date must not be null")
    private LocalDate startDate;

    @NotBlank(message = "End date must not be null")
    private LocalDate endDate;

    private TrainingStatus status;
}
