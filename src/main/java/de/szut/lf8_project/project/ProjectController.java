package de.szut.lf8_project.project;

import de.szut.lf8_project.project.dto.CreateProjectDto;
import de.szut.lf8_project.project.dto.UpdateProjectDto;
import de.szut.lf8_project.responses.ResponseHandler;
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


    @GetMapping("/{id}")
    @Operation(summary = "Get a project by ID")
    @ApiResponse(responseCode = "200", description = "Project by ID")
    @ApiResponse(responseCode = "401", description = "Not authorized")
    public ResponseEntity<ProjectEntity> getProjectById(@PathVariable Long id) {
        ProjectEntity project = projectService.getProjectById(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a project by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "401", description = "Not authorized",
                    content = @Content)
    })
    public ResponseEntity<ProjectEntity> updateProjectById(@PathVariable Long id, @RequestBody @Valid UpdateProjectDto updateProjectDto) {
        ProjectEntity project = projectService.updateProject(id, updateProjectDto);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @Operation(summary = "Deletes a project by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Project deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "401", description = "Not authorized",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseHandler.generateResponse("Project deleted by id:"+id, HttpStatus.NO_CONTENT,null);
    }
}


