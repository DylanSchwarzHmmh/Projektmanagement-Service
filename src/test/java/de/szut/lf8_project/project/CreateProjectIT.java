package de.szut.lf8_project.project;

import de.szut.lf8_project.project.dto.CreateProjectDto;
import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CreateProjectIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = "user")
    void testCreateProject() throws Exception {

        LocalDateTime startDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime estimatedEndDate = LocalDateTime.now().plusDays(10).truncatedTo(ChronoUnit.SECONDS);

        CreateProjectDto project = new CreateProjectDto();
        project.setDescription("Test Project");
        project.setCid(123L);
        project.setCustomerEmployeeName("John Doe");
        project.setComment("Test Comment");
        project.setStartDate(startDate);
        project.setEstimatedEndDate(estimatedEndDate);
        project.setEndDate(null);
        Set<Long> employees = new HashSet<>();
        employees.add(2L);
        project.setEmployees(employees);
        Set<Long> qualifications = new HashSet<>();
        qualifications.add(1L);
        qualifications.add(2L);
        project.setQualifications(qualifications);

        ResultActions result = mockMvc.perform(post("/v1/projects/")
                        .content(objectMapper.writeValueAsString(project))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));


        // Assert the response
        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Test Project"))
                .andExpect(jsonPath("$.cid").value(123L))
                .andExpect(jsonPath("$.customerEmployeeName").value("John Doe"))
                .andExpect(jsonPath("$.comment").value("Test Comment"))
                .andExpect(jsonPath("$.startDate").value(startDate.toString()))
                .andExpect(jsonPath("$.estimatedEndDate").value(estimatedEndDate.toString()))
                .andExpect(jsonPath("$.endDate").doesNotExist())
                .andExpect(jsonPath("$.employees").isArray())
                .andExpect(jsonPath("$.employees", hasSize(1)))
                .andExpect(jsonPath("$.employees[0]").value(2))
                .andExpect(jsonPath("$.qualifications").isArray())
                .andExpect(jsonPath("$.qualifications", hasSize(2)))
                .andExpect(jsonPath("$.qualifications[0]").value(1))
                .andExpect(jsonPath("$.qualifications[1]").value(2));
    }
}
