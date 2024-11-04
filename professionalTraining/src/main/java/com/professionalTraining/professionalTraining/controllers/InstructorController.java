package com.professionalTraining.professionalTraining.controllers;

import com.professionalTraining.professionalTraining.dto.InstructorDTO;
import com.professionalTraining.professionalTraining.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/instructors")
@Validated
public class InstructorController {
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping
    public ResponseEntity<InstructorDTO> createInstructor(@Valid @RequestBody InstructorDTO instructorDTO) {
        InstructorDTO savedInstructor = instructorService.saveInstructor(instructorDTO);
        return ResponseEntity.ok(savedInstructor);
    }

    @GetMapping
    public ResponseEntity<List<InstructorDTO>> getAllInstructors() {
        List<InstructorDTO> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDTO> getInstructorById(@PathVariable Long id) {
        InstructorDTO instructor = instructorService.getInstructorById(id);
        return ResponseEntity.ok(instructor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorDTO> updateInstructor(
            @PathVariable Long id, @Valid @RequestBody InstructorDTO updatedInstructorDTO) {
        InstructorDTO updatedInstructor = instructorService.updateInstructor(id, updatedInstructorDTO);
        return ResponseEntity.ok(updatedInstructor);
    }
}
