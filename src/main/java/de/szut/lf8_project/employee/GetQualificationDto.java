package de.szut.lf8_project.employee;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
public class GetQualificationDto {
    private Long id;
    private String skill;
}
