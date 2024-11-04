package com.professionalTraining.professionalTraining.services;
import com.professionalTraining.professionalTraining.dto.InstructorDTO;
import com.professionalTraining.professionalTraining.entities.Instructor;
import com.professionalTraining.professionalTraining.entities.Training;
import com.professionalTraining.professionalTraining.mapper.InstructorMapper;
import com.professionalTraining.professionalTraining.repositories.InstructorRepository;
import com.professionalTraining.professionalTraining.repositories.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class InstructorService {
    private static final Logger logger = LoggerFactory.getLogger(InstructorService.class);
    private final InstructorRepository instructorRepository;
    private final TrainingRepository trainingRepository;
    private final InstructorMapper instructorMapper;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository, TrainingRepository trainingRepository, InstructorMapper instructorMapper) {
        this.instructorRepository = instructorRepository;
        this.trainingRepository = trainingRepository;
        this.instructorMapper = instructorMapper;
    }

    public InstructorDTO saveInstructor(@Valid InstructorDTO instructorDTO) {
        try {
            Instructor instructor = instructorMapper.toEntity(instructorDTO);

            if (instructorDTO.getTrainingId() != null) {
                Training training = trainingRepository.findById(instructorDTO.getTrainingId())
                        .orElseThrow(() -> new RuntimeException("Training not found: " + instructorDTO.getTrainingId()));
                instructor.setTraining(training);
            }

            Instructor savedInstructor = instructorRepository.save(instructor);
            return instructorMapper.toDTO(savedInstructor);
        } catch (RuntimeException e) {
            logger.error("Error occurred while saving instructor: {}", e.getMessage());
            throw e; // Rethrow if you want to propagate it up
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
            throw new RuntimeException("Unexpected error occurred while saving instructor.");
        }
    }

    public InstructorDTO updateInstructor(Long id, @Valid InstructorDTO updatedInstructorDTO) {
        Instructor existingInstructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found: " + id));

        instructorMapper.updateEntityFromDTO(updatedInstructorDTO, existingInstructor);

        if (updatedInstructorDTO.getTrainingId() != null) {
            Training training = trainingRepository.findById(updatedInstructorDTO.getTrainingId())
                    .orElseThrow(() -> new RuntimeException("Training not found: " + updatedInstructorDTO.getTrainingId()));
            existingInstructor.setTraining(training);
        } else {
            existingInstructor.setTraining(null);
        }

        Instructor updatedInstructor = instructorRepository.save(existingInstructor);
        return instructorMapper.toDTO(updatedInstructor);
    }

    public List<InstructorDTO> getAllInstructors() {
        try {
            return instructorRepository.findAll()
                    .stream()
                    .map(instructorMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error retrieving instructors: {}", e.getMessage(), e);
            throw e;
        }
    }

    public InstructorDTO getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found: " + id));
        return instructorMapper.toDTO(instructor);
    }

    public void deleteInstructor(Long id) {
        if (!instructorRepository.existsById(id)) {
            throw new RuntimeException("Instructor not found: " + id);
        }
        instructorRepository.deleteById(id);
    }
}
