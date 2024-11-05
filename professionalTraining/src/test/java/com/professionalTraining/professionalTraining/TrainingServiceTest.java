package com.professionalTraining.professionalTraining;

import com.professionalTraining.professionalTraining.dto.TrainingDTO;
import com.professionalTraining.professionalTraining.entities.Training;
import com.professionalTraining.professionalTraining.entities.enums.TrainingStatus;
import com.professionalTraining.professionalTraining.exception.TrainingNotFoundException;
import com.professionalTraining.professionalTraining.mapper.TrainingMapper;
import com.professionalTraining.professionalTraining.repositories.TrainingRepository;
import com.professionalTraining.professionalTraining.services.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingService trainingService;

    private TrainingDTO trainingDTO;
    private Training training;

    @BeforeEach
    void setUp() {
        trainingDTO = new TrainingDTO();
        trainingDTO.setId(1L);
        trainingDTO.setTitle("Java Basics");
        trainingDTO.setLevel("Beginner");
        trainingDTO.setPrerequisites("None");
        trainingDTO.setMinCapacity(1);
        trainingDTO.setMaxCapacity(10);
        trainingDTO.setStartDate(LocalDate.of(2024, 1, 1));
        trainingDTO.setEndDate(LocalDate.of(2024, 1, 15));
        trainingDTO.setStatus(TrainingStatus.IN_PROGRESS);

        training = new Training();
        training.setId(1L);
        training.setTitle("Java Basics");
        training.setLevel("Beginner");
        training.setPrerequisites("None");
        training.setMinCapacity(1);
        training.setMaxCapacity(10);
        training.setStartDate(LocalDate.of(2024, 1, 1));
        training.setEndDate(LocalDate.of(2024, 1, 15));
        training.setStatus(TrainingStatus.IN_PROGRESS);
    }

    @Test
    void saveTraining_Success() {
        when(trainingMapper.toEntity(trainingDTO)).thenReturn(training);
        when(trainingRepository.save(training)).thenReturn(training);
        when(trainingMapper.toDTO(training)).thenReturn(trainingDTO);

        TrainingDTO savedTraining = trainingService.saveTraining(trainingDTO);

        assertNotNull(savedTraining);
        assertEquals("Java Basics", savedTraining.getTitle());
        verify(trainingRepository, times(1)).save(training);
    }

    @Test
    void getAllTrainings_Success() {
        List<Training> trainings = Arrays.asList(training);
        when(trainingRepository.findAll()).thenReturn(trainings);
        when(trainingMapper.toDTO(any(Training.class))).thenReturn(trainingDTO);

        List<TrainingDTO> trainingDTOList = trainingService.getAllTrainings();

        assertEquals(1, trainingDTOList.size());
        assertEquals("Java Basics", trainingDTOList.get(0).getTitle());
        verify(trainingRepository, times(1)).findAll();
    }

    @Test
    void getTrainingById_Success() {
        Long trainingId = 1L;
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(training));
        when(trainingMapper.toDTO(training)).thenReturn(trainingDTO);

        TrainingDTO foundTraining = trainingService.getTrainingById(trainingId);

        assertNotNull(foundTraining);
        assertEquals("Java Basics", foundTraining.getTitle());
        verify(trainingRepository, times(1)).findById(trainingId);
    }

    @Test
    void getTrainingById_NotFound() {
        Long trainingId = 1L;
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(TrainingNotFoundException.class, () -> trainingService.getTrainingById(trainingId));

        assertEquals("Training not found with ID: " + trainingId, exception.getMessage());
        verify(trainingRepository, times(1)).findById(trainingId);
    }

    @Test
    void deleteTraining_Success() {
        Long trainingId = 1L;
        when(trainingRepository.existsById(trainingId)).thenReturn(true);

        trainingService.deleteTraining(trainingId);

        verify(trainingRepository, times(1)).deleteById(trainingId);
    }

    @Test
    void deleteTraining_NotFound() {
        Long trainingId = 1L;
        when(trainingRepository.existsById(trainingId)).thenReturn(false);

        Exception exception = assertThrows(TrainingNotFoundException.class, () -> trainingService.deleteTraining(trainingId));

        assertEquals("Training not found with ID: " + trainingId, exception.getMessage());
        verify(trainingRepository, never()).deleteById(trainingId);
    }

    @Test
    void getTrainingsByTitle_Success() {
        String title = "Java Basics";
        when(trainingRepository.findByTitle(title)).thenReturn(Arrays.asList(training));
        when(trainingMapper.toDTO(any(Training.class))).thenReturn(trainingDTO);

        List<TrainingDTO> foundTrainings = trainingService.getTrainingsByTitle(title);

        assertEquals(1, foundTrainings.size());
        assertEquals("Java Basics", foundTrainings.get(0).getTitle());
        verify(trainingRepository, times(1)).findByTitle(title);
    }

    @Test
    void getTrainingsByStatus_Success() {
        String status = "IN_PROGRESS";
        when(trainingRepository.findByStatus(TrainingStatus.IN_PROGRESS)).thenReturn(Arrays.asList(training));
        when(trainingMapper.toDTO(any(Training.class))).thenReturn(trainingDTO);

        List<TrainingDTO> foundTrainings = trainingService.getTrainingsByStatus(status);

        assertEquals(1, foundTrainings.size());
        assertEquals("Java Basics", foundTrainings.get(0).getTitle());
        verify(trainingRepository, times(1)).findByStatus(TrainingStatus.IN_PROGRESS);
    }

    @Test
    void getTrainingsByStatus_InvalidStatus() {
        String status = "INVALID_STATUS";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> trainingService.getTrainingsByStatus(status));

        assertEquals("Invalid training status: " + status, exception.getMessage());
    }

    @Test
    void getTrainingsByTitleAndLevel_Success() {
        String title = "Java Basics";
        String level = "Beginner";
        when(trainingRepository.findByTitleAndLevel(title, level)).thenReturn(Arrays.asList(training));
        when(trainingMapper.toDTO(any(Training.class))).thenReturn(trainingDTO);

        List<TrainingDTO> foundTrainings = trainingService.getTrainingsByTitleAndLevel(title, level);

        assertEquals(1, foundTrainings.size());
        assertEquals("Java Basics", foundTrainings.get(0).getTitle());
        verify(trainingRepository, times(1)).findByTitleAndLevel(title, level);
    }
}
