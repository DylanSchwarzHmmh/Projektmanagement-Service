package de.szut.lf8_project.project;

import de.szut.lf8_project.project.dto.CreateProjectDto;
import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class CreateProjectIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = "user")
    void testCreateProject() throws Exception {
        // Given
        CreateProjectDto project = new CreateProjectDto();
        project.setDescription("Test Project");
        project.setCid(123L);
        project.setCustomerEmployeeName("John Doe");
        project.setComment("Test Comment");
        project.setStartDate(LocalDateTime.now());
        project.setEstimatedEndDate(LocalDateTime.now().plusDays(10));
        project.setEndDate(LocalDateTime.now().plusDays(11));
        Set<Long> employees = new HashSet<>();
        employees.add((long)(3));
        project.setEmployees(employees);
        Set<Long> qualifications = new HashSet<>();
        qualifications.add(1L);
        qualifications.add(2L);
        project.setQualifications(qualifications);

        /*MvcResult mvcResult = mockMvc.perform(post("/v1/projects/")
                        .content(objectMapper.writeValueAsString(project))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();


        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);*/
        ResultActions result = mockMvc.perform(post("/v1/projects/")
                        .content(objectMapper.writeValueAsString(project))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));


        // Assert the response
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Test Project"))
                .andExpect(jsonPath("$.cid").value(123L))
                .andExpect(jsonPath("$.customerEmployeeName").value("John Doe"))
                .andExpect(jsonPath("$.comment").value("Test Comment"));
    }

}
