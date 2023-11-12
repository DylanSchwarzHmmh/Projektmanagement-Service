package de.szut.lf8_project.project;

import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AddEmployeeToProjectIT extends AbstractIntegrationTest {
    @Test
    @WithMockUser
    void addEmployeeToProject() throws Exception {
        ResultActions result = mockMvc.perform(post("/v1/projects/1/addEmployee/2")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employees").isArray())
                .andExpect(jsonPath("$.employees", hasSize(2)))
                .andExpect(jsonPath("$.employees[1]").value(2L));
    }
}
