package de.szut.lf8_project.project;

import de.szut.lf8_project.exceptionHandling.ProjectNotFoundException;
import de.szut.lf8_project.project.dto.UpdateProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import de.szut.lf8_project.project.dto.CreateProjectDto;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectEntity createProject(CreateProjectDto createProjectDto) {
        ProjectEntity newProject = projectMapper.createProjectDtoToProjectEntity(createProjectDto);
        return projectRepository.save(newProject);
    }

    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }


    public ProjectEntity getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public ProjectEntity updateProject(Long id, UpdateProjectDto updateProjectDto) {
        ProjectEntity oldProject=projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        ProjectEntity newProject = projectMapper.updateProjectDtoToProjectEntity(updateProjectDto,oldProject);
        return projectRepository.save(newProject);
    }
}
