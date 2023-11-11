package de.szut.lf8_project.project;

import de.szut.lf8_project.project.dto.UpdateProjectDto;
import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AddEmployeeToProjectIT extends AbstractIntegrationTest {
    @Test
    @WithMockUser
    void addEmployeeToProject() throws Exception {

        UpdateProjectDto updateProjectDto = new UpdateProjectDto();
        Set<Long> employees = new HashSet<>();
        employees.add((long)(4));
        updateProjectDto.setEmployees(employees);

        String requestBodyJson = objectMapper.writeValueAsString(updateProjectDto);
        ResultActions result = mockMvc.perform(put("/v1/projects/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employees[0]").value(1L))
                .andExpect(jsonPath("$.employees[1]").value(4L));
    }
}
