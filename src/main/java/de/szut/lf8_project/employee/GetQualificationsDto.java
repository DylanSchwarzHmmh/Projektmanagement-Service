package de.szut.lf8_project.employee;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class GetQualificationsDto {
    private Long id;
    private String lastName;
    private String firstName;
    private Set<GetQualificationDto> skillSet;
}
