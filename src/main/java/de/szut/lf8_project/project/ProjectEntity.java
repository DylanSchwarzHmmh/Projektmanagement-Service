package de.szut.lf8_project.project;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max=500)
    private String description;

    @Column(nullable = false)
    private Long cid;

    @Column(nullable = false)
    private String customerEmployeeName;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime estimatedEndDate;

    @Column
    private LocalDateTime endDate;

    @ElementCollection
    @CollectionTable(name = "project_employees", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "employee_id")
    private Set<Long> employees;

    @ElementCollection
    @CollectionTable(name = "project_qualifications", joinColumns = @JoinColumn(name = "qualification_id"))
    @Column(name = "qualification")
    private Set<Long> qualifications;
}
