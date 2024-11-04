package com.professionalTraining.professionalTraining.controllers;

import com.professionalTraining.professionalTraining.dto.LearnerDTO;
import com.professionalTraining.professionalTraining.services.LearnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/learners")
@Validated
public class LearnerController {
    private final LearnerService learnerService;

    @Autowired
    public LearnerController(LearnerService learnerService) {
        this.learnerService = learnerService;
    }

    @PostMapping
    public ResponseEntity<LearnerDTO> createLearner(@Valid @RequestBody LearnerDTO learnerDTO) {
        LearnerDTO savedLearner = learnerService.saveLearner(learnerDTO);
        return ResponseEntity.ok(savedLearner);
    }

    @GetMapping
    public ResponseEntity<List<LearnerDTO>> getAllLearners() {
        List<LearnerDTO> learners = learnerService.getAllLearners();
        return ResponseEntity.ok(learners);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearnerDTO> getLearnerById(@PathVariable Long id) {
        LearnerDTO learner = learnerService.getLearnerById(id);
        return ResponseEntity.ok(learner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLearner(@PathVariable Long id) {
        learnerService.deleteLearner(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LearnerDTO> updateLearner(
            @PathVariable Long id, @Valid @RequestBody LearnerDTO updatedLearnerDTO) {
        LearnerDTO updatedLearner = learnerService.updateLearner(id, updatedLearnerDTO);
        return ResponseEntity.ok(updatedLearner);
    }

}
