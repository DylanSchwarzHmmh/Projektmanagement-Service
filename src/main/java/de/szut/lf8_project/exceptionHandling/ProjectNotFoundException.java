package de.szut.lf8_project.exceptionHandling;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(Long id) {
        super("The project with ID: " + id + ", couldn't be found!");
    }
}
