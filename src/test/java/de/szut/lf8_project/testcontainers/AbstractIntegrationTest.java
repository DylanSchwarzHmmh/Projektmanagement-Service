package de.szut.lf8_project.testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.szut.lf8_project.hello.HelloRepository;
import de.szut.lf8_project.project.ProjectEntity;
import de.szut.lf8_project.project.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("it")
@ContextConfiguration(initializers = PostgresContextInitializer.class)
@DirtiesContext
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

        // Create a sample project for testing
        for(int i = 0; i != 2 ; i++) {
            ProjectEntity project = new ProjectEntity();
            project.setId((long)i+1);
            project.setDescription("Test Project");
            project.setCid(123L);
            project.setCustomerEmployeeName("John Doe");
            project.setComment("Test Comment");
            project.setStartDate(LocalDateTime.now().plusDays((i+1)*10));
            project.setEstimatedEndDate(LocalDateTime.now().plusDays((i+1)*20));
            project.setEndDate(null);
            Set<Long> employees = new HashSet<>();
            employees.add(1L);
            project.setEmployees(employees);
            Set<Long> qualifications = new HashSet<>();
            qualifications.add(1L);
            qualifications.add(2L);
            project.setQualifications(qualifications);
            projectRepository.save(project);
        }
    }
}
