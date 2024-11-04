package com.professionalTraining.professionalTraining.repositories;

import com.professionalTraining.professionalTraining.entities.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnerRepository extends JpaRepository<Learner, Long> {

}
