package de.szut.lf8_project.testcontainers;

import de.szut.lf8_project.hello.HelloRepository;
import de.szut.lf8_project.project.ProjectEntity;
import de.szut.lf8_project.project.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A fast slice test will only start jpa context.
 * <p>
 * To use other context beans use org.springframework.context.annotation.@Import annotation.
 */
@DataJpaTest
@ActiveProfiles("database")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PostgresContextInitializer.class)
public class AbstractDatabaseTest {

    @Autowired
    protected HelloRepository helloRepository;

    @Autowired
    protected ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        helloRepository.deleteAll();
        projectRepository.deleteAll();
    }
}