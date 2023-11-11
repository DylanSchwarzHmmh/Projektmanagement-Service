package de.szut.lf8_project.project;

import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GetByIdIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = "user")
    void findById() throws Exception {
        ResultActions result = mockMvc.perform(get("/v1/projects/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("Test Project"))
                .andExpect(jsonPath("$.cid").value(123L))
                .andExpect(jsonPath("$.customerEmployeeName").value("John Doe"))
                .andExpect(jsonPath("$.comment").value("Test Comment"))
                .andExpect(jsonPath("$.employees[0]").value(1L));
    }
}

