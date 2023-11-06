package de.szut.lf8_project.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProjectDto {


    private String description;


    private Long cid;


    private String customerEmployeeName;


    private String comment;


    private LocalDateTime startDate;

    private LocalDateTime estimatedEndDate;


    private LocalDateTime endDate;

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<Long> getCid() {
        return Optional.ofNullable(cid);
    }

    public Optional<String> getCustomerEmployeeName() {
        return Optional.ofNullable(customerEmployeeName);
    }

    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }

    public Optional<LocalDateTime> getStartDate() {
        return Optional.ofNullable(startDate);
    }

    public Optional<LocalDateTime> getEstimatedEndDate() {
        return Optional.ofNullable(estimatedEndDate);
    }

    public Optional<LocalDateTime> getEndDate() {
        return Optional.ofNullable(endDate);
    }

}
