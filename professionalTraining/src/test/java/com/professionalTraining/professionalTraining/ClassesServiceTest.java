package com.professionalTraining.professionalTraining;

import com.professionalTraining.professionalTraining.dto.ClassesDTO;
import com.professionalTraining.professionalTraining.entities.Classes;
import com.professionalTraining.professionalTraining.exception.ClassesNotFoundException;
import com.professionalTraining.professionalTraining.mapper.ClassesMapper;
import com.professionalTraining.professionalTraining.repositories.ClassesRepository;
import com.professionalTraining.professionalTraining.services.ClassesService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClassesServiceTest {
    @Mock
    private ClassesRepository classesRepository;

    @Mock
    private ClassesMapper classesMapper;

    @InjectMocks
    private ClassesService classesService;

    private ClassesDTO classesDTO;
    private Classes classes;

    @BeforeEach
    void setUp() {
        classesDTO = new ClassesDTO();
        classesDTO.setName("Mathematics");

        classes = new Classes();
        classes.setId(1L);
        classes.setName("Mathematics");
    }

    @Test
    void saveClass_Success() {
        when(classesMapper.toEntity(classesDTO)).thenReturn(classes);
        when(classesRepository.save(classes)).thenReturn(classes);
        when(classesMapper.toDTO(classes)).thenReturn(classesDTO);

        ClassesDTO savedClass = classesService.saveClass(classesDTO);

        assertNotNull(savedClass);
        assertEquals("Mathematics", savedClass.getName());
        verify(classesRepository, times(1)).save(classes);
    }

    @Test
    void getAllClasses_Success() {
        List<Classes> classesList = Arrays.asList(classes);
        when(classesRepository.findAll()).thenReturn(classesList);
        when(classesMapper.toDTO(any(Classes.class))).thenReturn(classesDTO);

        List<ClassesDTO> classesDTOList = classesService.getAllClasses();

        assertEquals(1, classesDTOList.size());
        verify(classesRepository, times(1)).findAll();
    }

    @Test
    void getClassById_Success() {
        Long classId = 1L;
        when(classesRepository.findById(classId)).thenReturn(Optional.of(classes));
        when(classesMapper.toDTO(classes)).thenReturn(classesDTO);

        ClassesDTO foundClass = classesService.getClassById(classId);

        assertNotNull(foundClass);
        assertEquals("Mathematics", foundClass.getName());
        verify(classesRepository, times(1)).findById(classId);
    }

    @Test
    void getClassById_NotFound() {
        Long classId = 1L;
        when(classesRepository.findById(classId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ClassesNotFoundException.class, () -> classesService.getClassById(classId));
        System.out.println("Caught exception message: " + exception.getMessage());
        assertEquals("Class not found with ID: " + classId, exception.getMessage());
        verify(classesRepository, times(1)).findById(classId);
    }

    @Test
    void deleteClass_Success() {
        Long classId = 1L;
        when(classesRepository.existsById(classId)).thenReturn(true);

        classesService.deleteClass(classId);

        verify(classesRepository, times(1)).deleteById(classId);
    }

    @Test
    void deleteClass_NotFound() {
        Long classId = 1L;
        when(classesRepository.existsById(classId)).thenReturn(false);

        Exception exception = assertThrows(ClassesNotFoundException.class, () -> classesService.deleteClass(classId));

        assertEquals("Class not found with ID: " + classId, exception.getMessage());
        verify(classesRepository, never()).deleteById(classId);
    }

    @Test
    void updateClass_Success() {
        Long classId = 1L;
        when(classesRepository.findById(classId)).thenReturn(Optional.of(classes));
        when(classesRepository.save(classes)).thenReturn(classes);
        when(classesMapper.toDTO(classes)).thenReturn(classesDTO);

        ClassesDTO updatedClass = classesService.updateClass(classId, classesDTO);

        assertNotNull(updatedClass);
        assertEquals("Mathematics", updatedClass.getName());
        verify(classesRepository, times(1)).save(classes);
    }

}
