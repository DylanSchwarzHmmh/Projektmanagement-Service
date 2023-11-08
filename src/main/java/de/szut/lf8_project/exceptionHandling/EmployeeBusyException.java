package de.szut.lf8_project.exceptionHandling;

public class EmployeeBusyException extends RuntimeException {
    public EmployeeBusyException(Long id) {
        super("The employee with ID: " + id + ", is already busy in this time span!");
    }
}
