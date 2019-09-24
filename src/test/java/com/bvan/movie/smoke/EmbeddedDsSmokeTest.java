package com.bvan.movie.smoke;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@Sql(scripts = "classpath:sql/clear-h2.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class EmbeddedDsSmokeTest extends AbstractSmokeTest {}
