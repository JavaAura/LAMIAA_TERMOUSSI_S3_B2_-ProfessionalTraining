package com.professionalTraining.professionalTraining.exception;

public class LearnerNotFoundException  extends RuntimeException  {
    public LearnerNotFoundException(Long id) {
        super("Learner not found with id: " + id);
    }
}
