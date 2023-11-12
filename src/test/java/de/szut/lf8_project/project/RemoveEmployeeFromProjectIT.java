package de.szut.lf8_project.project;

import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RemoveEmployeeFromProjectIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = "user")
    void removeEmployee() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/projects/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Verify that the project has been deleted
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.employees").isArray())
                .andExpect(jsonPath("$.employees", hasSize(0)))
                .andExpect(jsonPath("$.employees", not(hasItem(1))));
    }
}
