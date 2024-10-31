package com.professionalTraining.professionalTraining.repositories;

import com.professionalTraining.professionalTraining.entities.Instructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long>{
}
