package com.bvan.movie;

import static org.assertj.core.api.Assertions.assertThat;

import com.bvan.movie.annotation.TestContainerDataSourceTest;
import com.bvan.movie.domain.Movie;
import com.bvan.movie.repository.MovieRepository;
import com.bvan.movie.util.TransactionTemplateHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@TestContainerDataSourceTest
class TrickyTransactionsTest {

    @Autowired
    private TransactionTemplateHelper transactionTemplateHelper;

    @Autowired
    private MovieRepository movieRepository;

    @AfterEach
    void tearDown() {
        Movie movie = transactionTemplateHelper.execute(() ->
                movieRepository.findMovieByTitleContaining("Mile"));
        assertThat(movie.getTitle()).isEqualTo("The Green Mile");
    }

    @Test
    @Transactional
    void useTransactionAnnotation() {
        // @Test and @AfterEach methods will be executed inside a single transaction
        // and will be rolled back after finish
        movieRepository.save(MovieFactory.theGreenMile());
    }

    @Test
    void useTransactionTemplate() {
        // Dedicate transaction with flushing into DB
        transactionTemplateHelper.execute(() -> movieRepository.save(MovieFactory.theGreenMile()));
    }
}
