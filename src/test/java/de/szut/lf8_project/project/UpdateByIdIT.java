package de.szut.lf8_project.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UpdateByIdIT extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void updateProjectById() throws Exception {
        // Create a sample project for testing
        ProjectEntity project = new ProjectEntity();
        project.setId(1L);
        project.setDescription("Test Project");
        project.setCid(123L);
        project.setCustomerEmployeeName("John Doe");
        project.setComment("Test Comment");
        project.setStartDate(LocalDateTime.now());
        project.setEstimatedEndDate(LocalDateTime.now().plusDays(10));
        project.setEndDate(null);
        Set<Long> employees = new HashSet<>();
        employees.add(1L);
        project.setEmployees(employees);


        String projectJson = objectMapper.writeValueAsString(project);

        ResultActions result = mockMvc.perform(put("/v1/projects/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(projectJson));

        // Assert the response
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("Test Project"))
                .andExpect(jsonPath("$.cid").value(123L))
                .andExpect(jsonPath("$.customerEmployeeName").value("John Doe"))
                .andExpect(jsonPath("$.comment").value("Test Comment"));
    }
}
