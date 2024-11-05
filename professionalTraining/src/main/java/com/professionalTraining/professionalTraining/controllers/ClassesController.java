package com.professionalTraining.professionalTraining.controllers;

import com.professionalTraining.professionalTraining.dto.ClassesDTO;
import com.professionalTraining.professionalTraining.services.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/classes")
@Validated
public class ClassesController {
    private final ClassesService classesService;

    @Autowired
    public ClassesController(ClassesService classesService) {
        this.classesService = classesService;
    }

    @PostMapping
    public ResponseEntity<ClassesDTO> createClass(@Valid @RequestBody ClassesDTO classesDTO) {
        ClassesDTO savedClass = classesService.saveClass(classesDTO);
        return ResponseEntity.ok(savedClass);
    }


    @GetMapping
    public ResponseEntity<Page<ClassesDTO>> getAllClasses(Pageable pageable) {
        Page<ClassesDTO> classesPage = classesService.getAllClasses(pageable);
        return ResponseEntity.ok(classesPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassesDTO> getClassById(@PathVariable Long id) {
        ClassesDTO classes = classesService.getClassById(id);
        return ResponseEntity.ok(classes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        classesService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassesDTO> updateClass(
            @PathVariable Long id, @Valid @RequestBody ClassesDTO updatedClassesDTO) {
        ClassesDTO updatedClass = classesService.updateClass(id, updatedClassesDTO);
        return ResponseEntity.ok(updatedClass);
    }
}
