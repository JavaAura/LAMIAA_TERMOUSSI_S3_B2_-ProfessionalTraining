package com.professionalTraining.professionalTraining.exception;

public class ClassesNotFoundException  extends RuntimeException {
    public ClassesNotFoundException(Long id) {
        super("Class not found with ID: " + id);
    }

}
