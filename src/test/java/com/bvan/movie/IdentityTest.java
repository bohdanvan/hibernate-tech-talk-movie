package com.bvan.movie;

import static com.bvan.movie.MovieFactory.theGreenMile;
import static org.assertj.core.api.Assertions.assertThat;

import com.bvan.movie.annotation.TestContainerDataSourceTest;
import com.bvan.movie.domain.Movie;
import com.bvan.movie.util.TransactionTemplateHelper;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@TestContainerDataSourceTest
class IdentityTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplateHelper transactionTemplateHelper;

    @Test
    void differentManagedEntitiesWithTheSameData() {
        transactionTemplateHelper.execute(() -> {
            Movie movieA = entityManager.merge(theGreenMile()); // Managed
            Movie movieB = entityManager.merge(theGreenMile()); // Managed

            assertThat(movieA == movieB).isFalse();
            assertThat(movieA.equals(movieB)).isFalse();
            assertThat(movieA.getId() == movieB.getId()).isFalse();
        });
    }

    @Test
    void newAndManagedEntity() {
        transactionTemplateHelper.execute(() -> {
            Movie movieA = theGreenMile(); // New (transient)
            Movie movieB = entityManager.merge(movieA); // Managed

            assertThat(movieA == movieB).isFalse();
            assertThat(movieA.equals(movieB)).isFalse();
            assertThat(movieA.getId() == movieB.getId()).isFalse();
        });
    }

    @Test
    void sameManagedEntity() {
        transactionTemplateHelper.execute(() -> {
            Movie movieA = entityManager.merge(theGreenMile());
            Movie movieB = entityManager.find(Movie.class, movieA.getId());

            assertThat(movieA == movieB).isTrue();
        });
    }

    @Test
    void detachedEntitiesWithTheSameId() {
        Movie movieA = transactionTemplateHelper.execute(() -> entityManager.merge(theGreenMile())); // Detached
        Movie movieB = transactionTemplateHelper
                .execute(() -> entityManager.find(Movie.class, movieA.getId())); // Detached

        assertThat(movieA == movieB).isFalse();
        assertThat(movieA.equals(movieB)).isFalse();
        assertThat(movieA.getId() == movieB.getId()).isTrue();
    }
}
