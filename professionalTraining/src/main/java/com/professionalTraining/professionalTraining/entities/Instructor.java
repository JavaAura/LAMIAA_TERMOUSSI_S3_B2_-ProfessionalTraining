package com.professionalTraining.professionalTraining.entities;


import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("INSTRUCTOR")
@AllArgsConstructor
@NoArgsConstructor
public class Instructor extends Person {

    @NotNull
    private String speciality;

    @OneToOne
    @JoinColumn(name = "class_id",unique = true)
    private Classes assignedClass;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;
}
