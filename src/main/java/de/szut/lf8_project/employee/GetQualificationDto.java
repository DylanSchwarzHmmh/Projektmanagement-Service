package de.szut.lf8_project.employee;

import lombok.Getter;
import lombok.Setter;
import reactor.util.annotation.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
public class GetQualificationDto {
    @Nullable
    private Long id;
    private String skill;
}
