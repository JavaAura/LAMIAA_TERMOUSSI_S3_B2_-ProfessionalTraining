package com.professionalTraining.professionalTraining.services;

import com.professionalTraining.professionalTraining.dto.TrainingDTO;
import com.professionalTraining.professionalTraining.entities.Training;
import com.professionalTraining.professionalTraining.entities.enums.TrainingStatus;
import com.professionalTraining.professionalTraining.exception.TrainingNotFoundException;
import com.professionalTraining.professionalTraining.mapper.TrainingMapper;
import com.professionalTraining.professionalTraining.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
    }

    public TrainingDTO saveTraining(@Valid TrainingDTO trainingDTO) {
        Training training = trainingMapper.toEntity(trainingDTO);
        Training savedTraining = trainingRepository.save(training);
        return trainingMapper.toDTO(savedTraining);
    }

    public List<TrainingDTO> getAllTrainings() {
        return trainingRepository.findAll()
                .stream()
                .map(trainingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TrainingDTO getTrainingById(Long id) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException(id));
        return trainingMapper.toDTO(training);
    }

    public void deleteTraining(Long id) {
        if (!trainingRepository.existsById(id)) {
            throw new TrainingNotFoundException(id);
        }
        trainingRepository.deleteById(id);
    }

    public TrainingDTO updateTraining(Long id, @Valid TrainingDTO updatedTrainingDTO) {
        Training existingTraining = trainingRepository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException(id));
        trainingMapper.updateEntityFromDTO(updatedTrainingDTO, existingTraining);
        Training updatedTraining = trainingRepository.save(existingTraining);
        return trainingMapper.toDTO(updatedTraining);
    }
    public List<TrainingDTO> getTrainingsByTitle(String title) {
        return trainingRepository.findByTitle(title).stream()
                .map(trainingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TrainingDTO> getTrainingsByStatus(String status) {
        try {
            TrainingStatus trainingStatus = TrainingStatus.valueOf(status.toUpperCase());
            return trainingRepository.findByStatus(trainingStatus).stream()
                    .map(trainingMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid training status: " + status);
        }
    }

    public List<TrainingDTO> getTrainingsByTitleAndLevel(String title, String level) {
        return trainingRepository.findByTitleAndLevel(title, level).stream()
                .map(trainingMapper::toDTO)
                .collect(Collectors.toList());
    }

}
