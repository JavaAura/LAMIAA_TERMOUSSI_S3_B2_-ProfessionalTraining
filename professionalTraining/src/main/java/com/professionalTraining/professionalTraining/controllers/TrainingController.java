package com.professionalTraining.professionalTraining.controllers;

import com.professionalTraining.professionalTraining.dto.TrainingDTO;
import com.professionalTraining.professionalTraining.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/trainings")
@Validated
public class TrainingController {
    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping
    public ResponseEntity<TrainingDTO> createTraining(@Valid @RequestBody TrainingDTO trainingDTO) {
        TrainingDTO savedTraining = trainingService.saveTraining(trainingDTO);
        return ResponseEntity.ok(savedTraining);
    }

    @GetMapping
    public ResponseEntity<List<TrainingDTO>> getAllTrainings() {
        List<TrainingDTO> trainings = trainingService.getAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDTO> getTrainingById(@PathVariable Long id) {
        TrainingDTO training = trainingService.getTrainingById(id);
        return ResponseEntity.ok(training);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingDTO> updateTraining(
            @PathVariable Long id, @Valid @RequestBody TrainingDTO updatedTrainingDTO) {
        TrainingDTO updatedTraining = trainingService.updateTraining(id, updatedTrainingDTO);
        return ResponseEntity.ok(updatedTraining);
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<TrainingDTO>> getTrainingsByTitle(@RequestParam String title) {
        return ResponseEntity.ok(trainingService.getTrainingsByTitle(title));
    }

    @GetMapping("/search/status")
    public ResponseEntity<List<TrainingDTO>> getTrainingsByStatus(@RequestParam String status) {
        return ResponseEntity.ok(trainingService.getTrainingsByStatus(status));
    }

    @GetMapping("/search/titleAndLevel")
    public ResponseEntity<List<TrainingDTO>> getTrainingsByTitleAndLevel(@RequestParam String title, @RequestParam String level) {
        return ResponseEntity.ok(trainingService.getTrainingsByTitleAndLevel(title, level));
    }
}
