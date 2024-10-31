package com.professionalTraining.professionalTraining.entities;


import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
@Entity
@DiscriminatorValue("LEARNER")
@AllArgsConstructor
@NoArgsConstructor
public class Learner  extends Person{

    @NotNull
    private String level;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classes assignedClass;
}
