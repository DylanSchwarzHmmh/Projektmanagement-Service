package de.szut.lf8_project.exceptionHandling;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(Long id) {
        super("Das Projekt mit der ID: " + id + ", konnte nicht gefunden werden!");
    }
}
