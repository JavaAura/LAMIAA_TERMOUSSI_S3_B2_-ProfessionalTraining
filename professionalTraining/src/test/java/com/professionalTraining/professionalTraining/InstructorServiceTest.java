package com.professionalTraining.professionalTraining;

import com.professionalTraining.professionalTraining.dto.InstructorDTO;
import com.professionalTraining.professionalTraining.entities.Instructor;
import com.professionalTraining.professionalTraining.entities.Training;
import com.professionalTraining.professionalTraining.mapper.InstructorMapper;
import com.professionalTraining.professionalTraining.repositories.InstructorRepository;
import com.professionalTraining.professionalTraining.repositories.TrainingRepository;
import com.professionalTraining.professionalTraining.services.InstructorService;
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
public class InstructorServiceTest {
    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private InstructorMapper instructorMapper;

    @InjectMocks
    private InstructorService instructorService;

    private InstructorDTO instructorDTO;
    private Instructor instructor;
    private Training training;

    @BeforeEach
    void setUp() {
        instructorDTO = new InstructorDTO();
        instructorDTO.setFirstname("Jane");
        instructorDTO.setLastname("Smith");
        instructorDTO.setEmail("jane.smith@example.com");
        instructorDTO.setSpeciality("Math");
        instructorDTO.setTrainingId(1L);

        instructor = new Instructor();
        instructor.setId(1L);
        instructor.setFirstname("Jane");
        instructor.setLastname("Smith");
        instructor.setEmail("jane.smith@example.com");
        instructor.setSpeciality("Math");

        training = new Training();
        training.setId(1L);
    }

    @Test
    void saveInstructor_Success() {
        when(instructorMapper.toEntity(instructorDTO)).thenReturn(instructor);
        when(trainingRepository.findById(instructorDTO.getTrainingId())).thenReturn(Optional.of(training));
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        when(instructorMapper.toDTO(instructor)).thenReturn(instructorDTO);

        InstructorDTO savedInstructor = instructorService.saveInstructor(instructorDTO);

        assertNotNull(savedInstructor);
        assertEquals("Jane", savedInstructor.getFirstname());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void saveInstructor_TrainingNotFound() {
        when(trainingRepository.findById(instructorDTO.getTrainingId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> instructorService.saveInstructor(instructorDTO));

        assertEquals("Training not found: " + instructorDTO.getTrainingId(), exception.getMessage());
        verify(instructorRepository, never()).save(any(Instructor.class));
    }

    @Test
    void updateInstructor_Success() {
        Long instructorId = 1L;
        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));
        when(trainingRepository.findById(instructorDTO.getTrainingId())).thenReturn(Optional.of(training));
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        when(instructorMapper.toDTO(instructor)).thenReturn(instructorDTO);

        InstructorDTO updatedInstructor = instructorService.updateInstructor(instructorId, instructorDTO);

        assertNotNull(updatedInstructor);
        assertEquals("Jane", updatedInstructor.getFirstname());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void getAllInstructors_Success() {
        List<Instructor> instructors = Arrays.asList(instructor);
        when(instructorRepository.findAll()).thenReturn(instructors);
        when(instructorMapper.toDTO(any(Instructor.class))).thenReturn(instructorDTO);

        List<InstructorDTO> instructorDTOList = instructorService.getAllInstructors();

        assertEquals(1, instructorDTOList.size());
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void getInstructorById_Success() {
        Long instructorId = 1L;
        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));
        when(instructorMapper.toDTO(instructor)).thenReturn(instructorDTO);

        InstructorDTO foundInstructor = instructorService.getInstructorById(instructorId);

        assertNotNull(foundInstructor);
        assertEquals("Jane", foundInstructor.getFirstname());
        verify(instructorRepository, times(1)).findById(instructorId);
    }

    @Test
    void getInstructorById_NotFound() {
        Long instructorId = 1L;
        when(instructorRepository.findById(instructorId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> instructorService.getInstructorById(instructorId));

        assertEquals("Instructor not found: " + instructorId, exception.getMessage());
        verify(instructorRepository, times(1)).findById(instructorId);
    }

    @Test
    void deleteInstructor_Success() {
        Long instructorId = 1L;
        when(instructorRepository.existsById(instructorId)).thenReturn(true);

        instructorService.deleteInstructor(instructorId);

        verify(instructorRepository, times(1)).deleteById(instructorId);
    }

    @Test
    void deleteInstructor_NotFound() {
        Long instructorId = 1L;
        when(instructorRepository.existsById(instructorId)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> instructorService.deleteInstructor(instructorId));

        assertEquals("Instructor not found: " + instructorId, exception.getMessage());
        verify(instructorRepository, never()).deleteById(instructorId);
    }
}
