package com.professionalTraining.professionalTraining;

import com.professionalTraining.professionalTraining.dto.LearnerDTO;
import com.professionalTraining.professionalTraining.entities.Learner;
import com.professionalTraining.professionalTraining.entities.Training;
import com.professionalTraining.professionalTraining.mapper.LearnerMapper;
import com.professionalTraining.professionalTraining.repositories.LearnerRepository;
import com.professionalTraining.professionalTraining.repositories.TrainingRepository;
import com.professionalTraining.professionalTraining.services.LearnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LearnerServiceTest {

    @Mock
    private LearnerRepository learnerRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private LearnerMapper learnerMapper;

    @InjectMocks
    private LearnerService learnerService;

    private LearnerDTO learnerDTO;
    private Learner learner;
    private Training training;

    @BeforeEach
    void setUp() {
        learnerDTO = new LearnerDTO();
        learnerDTO.setFirstname("John");
        learnerDTO.setLastname("Doe");
        learnerDTO.setEmail("john.doe@example.com");
        learnerDTO.setLevel("Beginner");
        learnerDTO.setTrainingId(1L);

        learner = new Learner();
        learner.setId(1L);
        learner.setFirstname("John");
        learner.setLastname("Doe");
        learner.setEmail("john.doe@example.com");
        learner.setLevel("Beginner");

        training = new Training();
        training.setId(1L);
    }

    @Test
    void saveLearner_Success() {
        when(learnerMapper.toEntity(learnerDTO)).thenReturn(learner);
        when(trainingRepository.findById(learnerDTO.getTrainingId())).thenReturn(Optional.of(training));
        when(learnerRepository.save(learner)).thenReturn(learner);
        when(learnerMapper.toDTO(learner)).thenReturn(learnerDTO);

        LearnerDTO savedLearner = learnerService.saveLearner(learnerDTO);

        assertNotNull(savedLearner);
        assertEquals("John", savedLearner.getFirstname());
        verify(learnerRepository, times(1)).save(learner);
    }

    @Test
    void saveLearner_TrainingNotFound() {
        when(trainingRepository.findById(learnerDTO.getTrainingId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> learnerService.saveLearner(learnerDTO));

        assertEquals("Training not found: " + learnerDTO.getTrainingId(), exception.getMessage());
        verify(learnerRepository, never()).save(any(Learner.class));
    }

    @Test
    void updateLearner_Success() {
        Long learnerId = 1L;
        when(learnerRepository.findById(learnerId)).thenReturn(Optional.of(learner));
        when(trainingRepository.findById(learnerDTO.getTrainingId())).thenReturn(Optional.of(training));
        when(learnerRepository.save(learner)).thenReturn(learner);
        when(learnerMapper.toDTO(learner)).thenReturn(learnerDTO);

        LearnerDTO updatedLearner = learnerService.updateLearner(learnerId, learnerDTO);

        assertNotNull(updatedLearner);
        assertEquals("John", updatedLearner.getFirstname());
        verify(learnerRepository, times(1)).save(learner);
    }

    @Test
    void getAllLearners_Success() {
        List<Learner> learners = Arrays.asList(learner);
        when(learnerRepository.findAll()).thenReturn(learners);
        when(learnerMapper.toDTO(any(Learner.class))).thenReturn(learnerDTO);

        List<LearnerDTO> learnerDTOList = learnerService.getAllLearners();

        assertEquals(1, learnerDTOList.size());
        verify(learnerRepository, times(1)).findAll();
    }

    @Test
    void getLearnerById_Success() {
        Long learnerId = 1L;
        when(learnerRepository.findById(learnerId)).thenReturn(Optional.of(learner));
        when(learnerMapper.toDTO(learner)).thenReturn(learnerDTO);

        LearnerDTO foundLearner = learnerService.getLearnerById(learnerId);

        assertNotNull(foundLearner);
        assertEquals("John", foundLearner.getFirstname());
        verify(learnerRepository, times(1)).findById(learnerId);
    }

    @Test
    void getLearnerById_NotFound() {
        Long learnerId = 1L;
        when(learnerRepository.findById(learnerId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> learnerService.getLearnerById(learnerId));

        assertEquals("Learner not found: " + learnerId, exception.getMessage());
        verify(learnerRepository, times(1)).findById(learnerId);
    }

    @Test
    void deleteLearner_Success() {
        Long learnerId = 1L;
        when(learnerRepository.existsById(learnerId)).thenReturn(true);

        learnerService.deleteLearner(learnerId);

        verify(learnerRepository, times(1)).deleteById(learnerId);
    }

    @Test
    void deleteLearner_NotFound() {
        Long learnerId = 1L;
        when(learnerRepository.existsById(learnerId)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> learnerService.deleteLearner(learnerId));

        assertEquals("Learner not found: " + learnerId, exception.getMessage());
        verify(learnerRepository, never()).deleteById(learnerId);
    }
}
