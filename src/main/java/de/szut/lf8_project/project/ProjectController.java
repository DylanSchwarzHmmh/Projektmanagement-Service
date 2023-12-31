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
import java.util.Map;
import java.util.Set;

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

    @Operation(summary = "Add an employee to a project")
    @ApiResponse(responseCode = "201", description = "Employee added to the project",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProjectEntity.class))})
    @ApiResponse(responseCode = "400", description = "Invalid JSON posted", content = @Content)
    @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content)
    @PostMapping("/{projectId}/addEmployee/{employeeId}")
    public ResponseEntity<ProjectEntity> addEmployeeToProject(
            @PathVariable Long projectId,
            @PathVariable Long employeeId
    ) {
        ProjectEntity updatedProject = projectService.addEmployee(projectId, employeeId);
        return new ResponseEntity<>(updatedProject, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a list of projects for specified employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of projects for employee",
                content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProjectEntity.class))}),
            @ApiResponse(responseCode = "401", description = "Not authorized",
            content = @Content)})
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Set<ProjectEntity>> getProjectsByEmployee(@PathVariable Long employeeId) {
        Set<ProjectEntity> projects = projectService.getAllProjectsByEmployeeId(employeeId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @Operation(summary = "Get a list of projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of projects",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectEntity.class))}),
            @ApiResponse(responseCode = "401", description = "Not authorized",
                    content = @Content)})
    @GetMapping("/")
    public ResponseEntity<Set<ProjectEntity>> getAllProjects() {
        Set<ProjectEntity> projects = projectService.getAllProjects();
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
        Map<String, Object> response = ResponseHandler
                .generateResponse("Project deleted by id:"+id, HttpStatus.OK, null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Removes an employee from a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee removed successfully"),
            @ApiResponse(responseCode = "404", description = "Project or employee not found"),
            @ApiResponse(responseCode = "401", description = "Not authorized")
    })
    @DeleteMapping("/{projectId}/{employeeId}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long projectId, @PathVariable Long employeeId) {
        projectService.deleteEmployee(projectId, employeeId);
        Map<String, Object> response = ResponseHandler
                .generateResponse("Removed employee with id: " + employeeId + " " +
                        "from project with id: " + projectId, HttpStatus.OK, null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}


