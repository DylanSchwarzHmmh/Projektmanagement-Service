package de.szut.lf8_project.project;

import de.szut.lf8_project.project.dto.UpdateProjectDto;
import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UpdateProjectByIdIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser
    void updateProjectById() throws Exception {

        // Create custom updateProjectDto
        UpdateProjectDto updateProjectDto = new UpdateProjectDto();
        updateProjectDto.setDescription("Neue Projektbeschreibung!");
        updateProjectDto.setCustomerEmployeeName("John Doe");
        updateProjectDto.setComment("Aktualisierter Kommentar");
        updateProjectDto.setCid(6L);

        // Map updateProjectDto to Json String
        String requestBodyJson = objectMapper.writeValueAsString(updateProjectDto);
        ResultActions result = mockMvc.perform(put("/v1/projects/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson));

        // Assert the response
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Neue Projektbeschreibung!"))
                .andExpect(jsonPath("$.customerEmployeeName").value("John Doe"))
                .andExpect(jsonPath("$.comment").value("Aktualisierter Kommentar"))
                .andExpect(jsonPath("$.cid").value(6L));
    }
}
