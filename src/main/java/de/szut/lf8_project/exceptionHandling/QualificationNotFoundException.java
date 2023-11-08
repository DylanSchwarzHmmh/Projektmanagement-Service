package de.szut.lf8_project.exceptionHandling;

public class QualificationNotFoundException extends RuntimeException {
    public QualificationNotFoundException(Long qualificationId) {
        super("The qualification with ID: " + qualificationId + " was not found!");
    }
}
