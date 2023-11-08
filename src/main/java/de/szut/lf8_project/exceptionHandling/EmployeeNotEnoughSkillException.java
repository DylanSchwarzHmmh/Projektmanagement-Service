package de.szut.lf8_project.exceptionHandling;

public class EmployeeNotEnoughSkillException extends RuntimeException {
    public EmployeeNotEnoughSkillException(Long employeeId) {
        super("The employee with id "+employeeId+" does not have the required skills to work on this project!");
    }
}
