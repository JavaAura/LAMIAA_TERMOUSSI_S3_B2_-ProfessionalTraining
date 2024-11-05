package com.professionalTraining.professionalTraining.services;

import com.professionalTraining.professionalTraining.dto.ClassesDTO;
import com.professionalTraining.professionalTraining.entities.Classes;

import com.professionalTraining.professionalTraining.exception.ClassesNotFoundException;
import com.professionalTraining.professionalTraining.mapper.ClassesMapper;
import com.professionalTraining.professionalTraining.repositories.ClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class ClassesService {

    private final ClassesRepository classesRepository;
    private final ClassesMapper classesMapper;

    @Autowired
    public ClassesService(ClassesRepository classesRepository, ClassesMapper classesMapper) {
        this.classesRepository = classesRepository;
        this.classesMapper = classesMapper;
    }

    public ClassesDTO saveClass(@Valid ClassesDTO classesDTO) {
        Classes classes = classesMapper.toEntity(classesDTO);
        Classes savedClass = classesRepository.save(classes);
        return classesMapper.toDTO(savedClass);
    }

    public Page<ClassesDTO> getAllClasses(Pageable pageable) {
        Page<Classes> classesPage = classesRepository.findAll(pageable);
        return classesPage.map(classesMapper::toDTO);
    }
    public ClassesDTO getClassById(Long id) {
        Classes classes = classesRepository.findById(id)
                .orElseThrow(() -> new ClassesNotFoundException(id));
        return classesMapper.toDTO(classes);
    }

    public void deleteClass(Long id) {
        if (!classesRepository.existsById(id)) {
            throw new ClassesNotFoundException(id);
        }
        classesRepository.deleteById(id);
    }

    public ClassesDTO updateClass(Long id, @Valid ClassesDTO updatedClassesDTO) {
        Classes existingClass = classesRepository.findById(id)
                .orElseThrow(() -> new ClassesNotFoundException(id));
        classesMapper.updateEntityFromDTO(updatedClassesDTO, existingClass);
        Classes updatedClass = classesRepository.save(existingClass);
        return classesMapper.toDTO(updatedClass);
    }
}
