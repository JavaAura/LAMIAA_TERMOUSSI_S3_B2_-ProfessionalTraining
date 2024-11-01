package com.professionalTraining.professionalTraining.exception;

public class TrainingNotFoundException extends RuntimeException{

    public TrainingNotFoundException(Long id) {
        super("Training not found with ID: " + id);
    }
}
