package com.professionalTraining.professionalTraining.entities;


import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("INSTRUCTOR")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Instructor extends Person {

    @NotNull
    private String speciality;

    @OneToOne(mappedBy = "instructor")
    private Classes assignedClass;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;
}
