package de.szut.lf8_project.exceptionHandling;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(Long id) {
        super("The employee with ID: " + id + ", couldn't be found!");
    }
}
