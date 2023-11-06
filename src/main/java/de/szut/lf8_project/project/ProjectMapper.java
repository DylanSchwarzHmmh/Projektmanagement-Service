package de.szut.lf8_project.project;

import de.szut.lf8_project.project.dto.CreateProjectDto;
import de.szut.lf8_project.project.dto.UpdateProjectDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public ProjectEntity createProjectDtoToProjectEntity(@NotNull CreateProjectDto createProjectDto) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setCid(createProjectDto.getCid());
        projectEntity.setEid(createProjectDto.getEid());
        projectEntity.setDescription(createProjectDto.getDescription());
        projectEntity.setCustomerEmployeeName(createProjectDto.getCustomerEmployeeName());
        projectEntity.setComment(createProjectDto.getComment());
        projectEntity.setStartDate(createProjectDto.getStartDate());
        projectEntity.setEstimatedEndDate(createProjectDto.getEstimatedEndDate());
        projectEntity.setEndDate(createProjectDto.getEndDate());
        return projectEntity;
    }

    public ProjectEntity updateProjectDtoToProjectEntity(@NotNull UpdateProjectDto updateProjectDto, ProjectEntity oldProject) {
        updateProjectDto.getDescription().ifPresent(oldProject::setDescription);
        updateProjectDto.getCid().ifPresent(oldProject::setCid);
        updateProjectDto.getCustomerEmployeeName().ifPresent(oldProject::setCustomerEmployeeName);
        updateProjectDto.getComment().ifPresent(oldProject::setComment);
        updateProjectDto.getStartDate().ifPresent(oldProject::setStartDate);
        updateProjectDto.getEstimatedEndDate().ifPresent(oldProject::setEstimatedEndDate);
        updateProjectDto.getEndDate().ifPresent(oldProject::setEndDate);
        return oldProject;
    }
}
