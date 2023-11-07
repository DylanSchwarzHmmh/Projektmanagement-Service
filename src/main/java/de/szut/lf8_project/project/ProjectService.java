package de.szut.lf8_project.project;

import de.szut.lf8_project.employee.EmployeeManagementMicroService;
import de.szut.lf8_project.employee.GetEmployeeDto;
import de.szut.lf8_project.exceptionHandling.EmployeeAlreadyExistsException;
import de.szut.lf8_project.exceptionHandling.EmployeeNotFoundException;
import de.szut.lf8_project.exceptionHandling.ProjectNotFoundException;
import de.szut.lf8_project.project.dto.UpdateProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import de.szut.lf8_project.project.dto.CreateProjectDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final EmployeeManagementMicroService microService;

    public ProjectEntity createProject(CreateProjectDto createProjectDto) {
        ProjectEntity newProject = projectMapper.createProjectDtoToProjectEntity(createProjectDto);
        return projectRepository.save(newProject);
    }

    public ProjectEntity addEmployee(Long projectId, Long employeeId) {
        // Get project by its ID
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() ->
                new ProjectNotFoundException(projectId));

        // Check if employee exists
        if(microService.getEmployeeById(employeeId) == null) throw new EmployeeNotFoundException(employeeId);

        // Get old Employee list
        Set<Long> oldEmployeeList = project.getEmployees();

        // Check if employee already works on this project
        if(oldEmployeeList.contains(employeeId)) throw new EmployeeAlreadyExistsException(employeeId);

        // Add new EmployeeId to the database
        oldEmployeeList.add(employeeId);
        project.setEmployees(oldEmployeeList);

        // Save and return project
        return projectRepository.save(project);
    }

    public List<ProjectEntity> getAllProjects() { return projectRepository.findAll(); }

    public ProjectEntity getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public ProjectEntity updateProject(Long id, UpdateProjectDto updateProjectDto) {
        ProjectEntity oldProject=projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        ProjectEntity newProject = projectMapper.updateProjectDtoToProjectEntity(updateProjectDto,oldProject);
        return projectRepository.save(newProject);
    }

    public void deleteProject(Long id) {
        ProjectEntity projectToDelete = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        projectRepository.delete(projectToDelete);
    }
}
