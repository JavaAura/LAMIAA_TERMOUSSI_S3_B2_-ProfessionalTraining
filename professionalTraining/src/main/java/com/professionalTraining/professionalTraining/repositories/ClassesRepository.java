package com.professionalTraining.professionalTraining.repositories;

import com.professionalTraining.professionalTraining.entities.Classes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long> {
    Optional<Classes> findById(Long id);
    Page<Classes> findAll(Pageable pageable);
}
