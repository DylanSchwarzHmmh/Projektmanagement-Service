package de.szut.lf8_project.project;

import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class DeleteProjectIT extends AbstractIntegrationTest {

    @Test
    @WithMockUser(roles = "user")
    void deleteProject() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Verify that the project has been deleted
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/projects/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}


// Bei "deleteProject" im ProjectController, beim normalen  Return:
// return ResponseEntity.ok(response);
// funktioniert "DeleteProjectIT" nicht
//
// Bei diesen Varianten würde es klappen:
// return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
// return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
//