package de.szut.lf8_project.project;

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

}
