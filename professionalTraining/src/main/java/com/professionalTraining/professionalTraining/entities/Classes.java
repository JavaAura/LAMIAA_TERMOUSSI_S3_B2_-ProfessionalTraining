package com.professionalTraining.professionalTraining.entities;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer roomNum;

    @OneToOne
    @JoinColumn(name = "instructor_id", unique = true)
    private Instructor instructor;

    @OneToMany(mappedBy = "assignedClass")
    private List<Learner> learners;

}
