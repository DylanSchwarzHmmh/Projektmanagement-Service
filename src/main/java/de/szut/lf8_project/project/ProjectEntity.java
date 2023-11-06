package de.szut.lf8_project.project;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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
    private Long eid;

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

    @Column(nullable = false)
    private LocalDateTime endDate;
}
