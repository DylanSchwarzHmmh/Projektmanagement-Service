package de.szut.lf8_project.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class GetEmployeeDto {
    private Long id;
    private String lastName;
    private String firstName;
    private String street;
    private String postcode;
    private String city;
    private String phone;
    private Set<GetQualificationDto> qualifications;
}
