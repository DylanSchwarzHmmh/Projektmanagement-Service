package de.szut.lf8_project.project;

import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class GetAllProjectsIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = "user")
    void getAllProjects() throws Exception {
        // Performing the request and verifying the response
        ResultActions result = mockMvc.perform(get("/v1/projects/")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].description").value("Test Project"))
                .andExpect(jsonPath("$[0].cid").value(123L))
                .andExpect(jsonPath("$[0].customerEmployeeName").value("John Doe"))
                .andExpect(jsonPath("$[0].comment").value("Test Comment"))
                .andExpect(jsonPath("$[0].endDate").doesNotExist())
                .andExpect(jsonPath("$[0].employees[0]").value(1L))
                .andExpect(jsonPath("$[0].qualifications[0]").value(1))
                .andExpect(jsonPath("$[0].qualifications[1]").value(2))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].description").value("Test Project"))
                .andExpect(jsonPath("$[1].cid").value(123L))
                .andExpect(jsonPath("$[1].customerEmployeeName").value("John Doe"))
                .andExpect(jsonPath("$[1].comment").value("Test Comment"))
                .andExpect(jsonPath("$[1].endDate").doesNotExist())
                .andExpect(jsonPath("$[1].employees[0]").value(1L))
                .andExpect(jsonPath("$[1].qualifications[0]").value(1))
                .andExpect(jsonPath("$[1].qualifications[1]").value(2));
    }
}
