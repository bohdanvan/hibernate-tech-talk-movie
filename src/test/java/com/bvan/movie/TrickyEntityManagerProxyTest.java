package com.bvan.movie;

import static org.assertj.core.api.Assertions.assertThat;

import com.bvan.movie.annotation.TestContainerDataSourceTest;
import com.bvan.movie.domain.Movie;
import com.bvan.movie.util.TransactionTemplateHelper;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@TestContainerDataSourceTest
class TrickyEntityManagerProxyTest {

    @Autowired
    private TransactionTemplateHelper transactionTemplateHelper;

    @Autowired
    private EntityManager entityManager; // Shared Spring Proxy

    @Test
    void useSingleTransaction_singleEntityManagerBehindSpringProxy() {
        transactionTemplateHelper.execute(() -> {
            Movie movie1 = entityManager.merge(MovieFactory.theGreenMile());
            Movie movie2 = entityManager.find(Movie.class, movie1.getId());

            assertThat(movie1 == movie2).isTrue();
        });
    }

    @Test
    void useTwoTransactions_twoEntityManagersBehindSpringProxy() {
        Movie movie1 = transactionTemplateHelper.execute(() -> entityManager.merge(MovieFactory.theGreenMile()));

        transactionTemplateHelper.execute(() -> {
            Movie movie2 = entityManager.find(Movie.class, movie1.getId());
            assertThat(movie1 == movie2).isFalse();
        });
    }
}














