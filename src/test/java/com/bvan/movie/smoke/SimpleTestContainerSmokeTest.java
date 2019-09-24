package com.bvan.movie.smoke;

import com.bvan.movie.smoke.SimpleTestContainerSmokeTest.SpringInitializer;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Ignore
@SpringBootTest
@Testcontainers
@Sql(scripts = "classpath:sql/clear-postgres.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@ContextConfiguration(initializers = SpringInitializer.class)
class SimpleTestContainerSmokeTest extends AbstractSmokeTest {

    @Container
    private static final JdbcDatabaseContainer POSTGRESQL = new PostgreSQLContainer()
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test-movie");

    static class SpringInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Autowired
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + POSTGRESQL.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRESQL.getUsername(),
                    "spring.datasource.password=" + POSTGRESQL.getPassword(),
                    "spring.jpa.hibernate.ddl-auto=update"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
