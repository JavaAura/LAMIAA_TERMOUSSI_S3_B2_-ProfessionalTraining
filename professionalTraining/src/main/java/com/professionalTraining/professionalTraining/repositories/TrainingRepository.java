package com.professionalTraining.professionalTraining.repositories;

import com.professionalTraining.professionalTraining.entities.Training;
import com.professionalTraining.professionalTraining.entities.enums.TrainingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByTitle(String title);
    List<Training> findByStatus(TrainingStatus status);
    List<Training> findByTitleAndLevel(String title, String level);
}
