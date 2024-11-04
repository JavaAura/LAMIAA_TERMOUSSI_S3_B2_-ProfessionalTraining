package com.professionalTraining.professionalTraining.services;

import com.professionalTraining.professionalTraining.dto.LearnerDTO;
import com.professionalTraining.professionalTraining.entities.Learner;
import com.professionalTraining.professionalTraining.entities.Training;
import com.professionalTraining.professionalTraining.mapper.LearnerMapper;
import com.professionalTraining.professionalTraining.repositories.LearnerRepository;
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
public class LearnerService {

    private static final Logger logger = LoggerFactory.getLogger(LearnerService.class);
    private final LearnerRepository learnerRepository;
    private final TrainingRepository trainingRepository;
    private final LearnerMapper learnerMapper;

    @Autowired
    public LearnerService(LearnerRepository learnerRepository,TrainingRepository trainingRepository, LearnerMapper learnerMapper) {
        this.learnerRepository = learnerRepository;
        this.trainingRepository = trainingRepository;
        this.learnerMapper = learnerMapper;
    }

    public LearnerDTO saveLearner(@Valid LearnerDTO learnerDTO) {
        try {
            System.out.println(learnerDTO);
            Learner learner = learnerMapper.toEntity(learnerDTO);

            if (learnerDTO.getTrainingId() != null) {
                Training training = trainingRepository.findById(learnerDTO.getTrainingId())
                        .orElseThrow(() -> new RuntimeException("Training not found: " + learnerDTO.getTrainingId()));
                learner.setTraining(training);
            }

            Learner savedLearner = learnerRepository.save(learner);
            return learnerMapper.toDTO(savedLearner);
        } catch (RuntimeException e) {
            // Log the exception message
            System.err.println("Error occurred while saving learner: " + e.getMessage());
            // Optionally rethrow the exception or return null
            throw e; // Rethrow if you want to propagate it up
        } catch (Exception e) {
            // Catch other exceptions if needed
            System.err.println("An unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while saving learner.");
        }
    }
    public LearnerDTO updateLearner(Long id, @Valid LearnerDTO updatedLearnerDTO) {
        Learner existingLearner = learnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Learner not found: " + id));

        learnerMapper.updateEntityFromDTO(updatedLearnerDTO, existingLearner);

        if (updatedLearnerDTO.getTrainingId() != null) {
            Training training = trainingRepository.findById(updatedLearnerDTO.getTrainingId())
                    .orElseThrow(() -> new RuntimeException("Training not found: " + updatedLearnerDTO.getTrainingId()));
            existingLearner.setTraining(training);
        } else {
            existingLearner.setTraining(null);
        }

        Learner updatedLearner = learnerRepository.save(existingLearner);
        return learnerMapper.toDTO(updatedLearner);
    }
    public List<LearnerDTO> getAllLearners() {
        try {
            return learnerRepository.findAll()
                    .stream()
                    .map(learnerMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Log the error
            logger.error("Error retrieving learners: {}", e.getMessage(), e);
            throw e; // Optionally rethrow the exception
        }
    }
    public LearnerDTO getLearnerById(Long id) {
        Learner learner = learnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Learner not found: " + id));
        return learnerMapper.toDTO(learner);
    }
    public void deleteLearner(Long id) {
        if (!learnerRepository.existsById(id)) {
            throw new RuntimeException("Learner not found: " + id);
        }
        learnerRepository.deleteById(id);
    }

}
