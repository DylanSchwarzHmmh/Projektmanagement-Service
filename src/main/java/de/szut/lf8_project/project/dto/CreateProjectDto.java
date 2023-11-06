package de.szut.lf8_project.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CreateProjectDto {
    @Valid
    private String description;

    @NonNull
    private Long eid;

    @NonNull
    private Long cid;

    @NonNull
    private String customerEmployeeName;

    @NonNull
    private String comment;

    @NonNull
    private LocalDateTime startDate;

    @NonNull
    private LocalDateTime estimatedEndDate;

    @NonNull
    private LocalDateTime endDate;
}