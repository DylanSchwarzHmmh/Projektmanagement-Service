package de.szut.lf8_project.testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.szut.lf8_project.hello.HelloRepository;
import de.szut.lf8_project.project.ProjectEntity;
import de.szut.lf8_project.project.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A fast slice test will only start jpa context.
 * <p>
 * To use other context beans use org.springframework.context.annotation.@Import annotation.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("it")
@ContextConfiguration(initializers = PostgresContextInitializer.class)
public class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected HelloRepository helloRepository;

    @Autowired
    protected ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        helloRepository.deleteAll();

        // Delete all projects
        projectRepository.deleteAll();

        // Create and save 3 new projects
        for(int i = 0; i < 3;i++) {
            // Create a sample project for testing
            ProjectEntity project = new ProjectEntity();
            project.setId((long)(i+1));
            project.setDescription("Test Project");
            project.setCid(123L);
            project.setCustomerEmployeeName("John Doe");
            project.setComment("Test Comment");
            project.setStartDate(LocalDateTime.now());
            project.setEstimatedEndDate(LocalDateTime.now().plusDays(10));
            project.setEndDate(null);
            Set<Long> employees = new HashSet<>();
            employees.add((long)(i+1));
            project.setEmployees(employees);
            Set<Long> qualifications = new HashSet<>();
            qualifications.add(1L);
            qualifications.add(2L);
            project.setQualifications(qualifications);
            projectRepository.save(project);

        }
    }
}
