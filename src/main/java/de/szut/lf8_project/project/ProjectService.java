package de.szut.lf8_project.project;

import de.szut.lf8_project.employee.EmployeeManagementMicroService;
import de.szut.lf8_project.employee.GetEmployeeDto;
import de.szut.lf8_project.employee.GetQualificationDto;
import de.szut.lf8_project.employee.GetQualificationsDto;
import de.szut.lf8_project.exceptionHandling.*;
import de.szut.lf8_project.project.dto.UpdateProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import de.szut.lf8_project.project.dto.CreateProjectDto;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final EmployeeManagementMicroService microService;

    public ProjectEntity createProject(CreateProjectDto createProjectDto) {
        ProjectEntity newProject = projectMapper.createProjectDtoToProjectEntity(createProjectDto);
        checkProjectBody(newProject);
        return projectRepository.save(newProject);
    }

    public ProjectEntity addEmployee(Long projectId, Long employeeId) {
        // Get project by its ID
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() ->
                new ProjectNotFoundException(projectId));

        // Check if employee exists
        if(microService.getEmployeeById(employeeId) == null) {
            throw new EmployeeNotFoundException(employeeId);
        }

        // Check if employee is already busy
        if(employeeIsBusy(project, employeeId)) {
            throw new EmployeeBusyException(employeeId);
        }

        // Get old Employee list
        Set<Long> oldEmployeeList = project.getEmployees();

        // Check if employee already works on this project
        if(oldEmployeeList.contains(employeeId)) {
            throw new EmployeeAlreadyExistsException(employeeId);
        }

        // Add new EmployeeId to the database
        oldEmployeeList.add(employeeId);
        project.setEmployees(oldEmployeeList);

        // Save and return project
        return projectRepository.save(project);
    }

    public Set<ProjectEntity> getAllProjects() { return new HashSet<ProjectEntity>(projectRepository.findAll()); }

    public ProjectEntity getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public ProjectEntity updateProject(Long id, UpdateProjectDto updateProjectDto) {

        // Check if the project exists
        ProjectEntity oldProject=projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        ProjectEntity newProject = projectMapper.updateProjectDtoToProjectEntity(updateProjectDto, oldProject);

        // Check the new project body for valid values
        checkProjectBody(newProject);

        // Return the new repository
        return projectRepository.save(newProject);
    }

    public void deleteProject(Long id) {
        ProjectEntity projectToDelete = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        projectRepository.delete(projectToDelete);
    }
    
    public void deleteEmployee(Long projectId, Long employeeId) {
        ProjectEntity myProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        Set<Long> employees = myProject.getEmployees();
        if(employees.contains(employeeId)) {
            employees.remove(employeeId);
            myProject.setEmployees(employees);
        } else {
            throw new EmployeeNotFoundException(employeeId);
        }
        projectRepository.save(myProject);
    }

    public boolean employeeIsBusy(ProjectEntity newProject, Long employeeId) {
        Set<ProjectEntity> employeeProjects = getAllProjectsByEmployeeId(employeeId);
        if(employeeProjects.isEmpty()) return false;
        for(ProjectEntity project : employeeProjects) {
            if(!project.getId().equals(newProject.getId())) {
                LocalDateTime start = project.getStartDate();
                LocalDateTime estimatedEnd = project.getEstimatedEndDate();
                LocalDateTime newStart = newProject.getStartDate();
                LocalDateTime newEstimatedEnd = newProject.getEstimatedEndDate();
                if (!(newEstimatedEnd.isBefore(start) || newStart.isAfter(estimatedEnd))) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks if a Update or Create body is valid or not
    public void checkProjectBody(ProjectEntity newProject) {
        Set<GetEmployeeDto> employeeDtos = microService.getAllEmployees();
        Set<GetQualificationDto> allQualifications = microService.getAllQualifications();
        Set<Long> projectQualifications = newProject.getQualifications();

        // Check if realEndDate is after the start date
        LocalDateTime startDate = newProject.getStartDate();
        LocalDateTime estimatedEndDate = newProject.getEstimatedEndDate();
        if(!estimatedEndDate.isAfter(startDate)) {
            throw new DateTimeException("The date you entered is not valid!");
        }

        // Loop through all EmployeeIds of the newProject
        for(Long newEmployeeId : newProject.getEmployees()) {

            // Check if the employee exists
            if(employeeDtos.stream().noneMatch(employeeDto -> employeeDto.getId().equals(newEmployeeId))) {
                throw new EmployeeNotFoundException(newEmployeeId);
            }

            // Check if employee is Busy
            if(employeeIsBusy(newProject, newEmployeeId)) {
                throw new EmployeeBusyException(newEmployeeId);
            }

            // Check if projectQualifications exist
            for (Long qualificationId : projectQualifications) {
                if (allQualifications.stream().noneMatch(qualificationDto -> {
                    assert qualificationDto.getId() != null;
                    return qualificationDto.getId().equals(qualificationId);
                })) {
                    throw new QualificationNotFoundException(newEmployeeId);
                }
            }

            // Check if employeeQualifications are correct
            GetQualificationsDto employeeQualifications = microService.getQualificationsForEmployee(newEmployeeId);
            if(newProject.getQualifications()
                    .stream().noneMatch(newProjectQualification -> newProjectQualification.equals(employeeQualifications.getId()))) {
                throw new EmployeeNotEnoughSkillException(newEmployeeId);
            }
        }
    }

    public Set<ProjectEntity> getAllProjectsByEmployeeId(Long employeeId) {
        Set<ProjectEntity> projects = new HashSet<>();
        List<ProjectEntity> allProjects = projectRepository.findAll();
        for(ProjectEntity project : allProjects) {
            if (project.getEmployees().contains(employeeId)) {
                projects.add(project);
            }
        }
        return projects;
    }
}
