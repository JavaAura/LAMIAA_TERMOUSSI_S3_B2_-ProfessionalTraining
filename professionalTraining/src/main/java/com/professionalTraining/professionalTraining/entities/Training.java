package com.professionalTraining.professionalTraining.entities;


import com.professionalTraining.professionalTraining.entities.enums.TrainingStatus;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String level;

    private String prerequisites;

    @NotNull
    private Integer minCapacity;

    @NotNull
    private Integer maxCapacity;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private TrainingStatus status;

    @OneToMany(mappedBy = "training")
    private List<Instructor> instructors;

    @OneToMany(mappedBy = "training")
    private List<Learner> learners;

}
