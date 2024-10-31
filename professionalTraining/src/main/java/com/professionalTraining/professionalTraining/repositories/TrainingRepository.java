package com.professionalTraining.professionalTraining.repositories;

import com.professionalTraining.professionalTraining.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
}
