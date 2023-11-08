package de.szut.lf8_project.exceptionHandling;

public class EmployeesNotFoundException extends RuntimeException {
    public EmployeesNotFoundException() {
        super("Employees not found!");
    }
}
