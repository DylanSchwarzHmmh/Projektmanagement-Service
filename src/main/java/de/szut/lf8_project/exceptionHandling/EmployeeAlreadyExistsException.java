package de.szut.lf8_project.exceptionHandling;

public class EmployeeAlreadyExistsException extends RuntimeException {
    public EmployeeAlreadyExistsException(Long id) {
        super("The employee with ID: " + id + " is already working on this project!");
    }
}
