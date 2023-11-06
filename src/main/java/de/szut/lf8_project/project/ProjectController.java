package de.szut.lf8_project.project;

import de.szut.lf8_project.project.dto.CreateProjectDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Creates a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Not authorized",
                    content = @Content)})
    @PostMapping("/")
    public ResponseEntity<ProjectEntity> createProject(@RequestBody @Valid CreateProjectDto createProjectDto) {
        ProjectEntity createdProject = projectService.createProject(createProjectDto);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a list of projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of projects",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectEntity.class))}),
            @ApiResponse(responseCode = "401", description = "Not authorized",
                    content = @Content)})
    @GetMapping("/")
    public ResponseEntity<List<ProjectEntity>> getAllProjects() {
        List<ProjectEntity> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

}
