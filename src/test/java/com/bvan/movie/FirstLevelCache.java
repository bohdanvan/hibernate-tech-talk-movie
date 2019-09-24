package com.bvan.movie;

import static org.assertj.core.api.Assertions.assertThat;

import com.bvan.movie.annotation.TestContainerDataSourceTest;
import com.bvan.movie.domain.Movie;
import com.bvan.movie.util.TransactionTemplateHelper;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@TestContainerDataSourceTest
class FirstLevelCache {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplateHelper transactionTemplateHelper;

    @Test
    void newAndManagedEntity_dirtyChecking() {
        transactionTemplateHelper.execute(() -> {
            Movie movie = MovieFactory.theGreenMile(); // New (Transient) entity
            entityManager.persist(movie); // Managed entity
            movie.setReleaseYear(2000); // Dirty checking
        });

        Movie movie = transactionTemplateHelper
                .execute(() -> entityManager
                        .createQuery("select m from Movie m where m.title like :title", Movie.class)
                        .setParameter("title", "%Mile%")
                        .getSingleResult());

        assertThat(movie.getReleaseYear()).isEqualTo(2000);
    }

    @Test
    void managedEntity_dirtyChecking() {
        transactionTemplateHelper.execute(() -> {
            Movie movie = MovieFactory.theGreenMile(); // New (Transient) entity
            entityManager.persist(movie); // Managed entity
        });

        Movie persistedMovie = transactionTemplateHelper.execute(() -> {
            Movie movie = transactionTemplateHelper
                    .execute(() -> entityManager
                            .createQuery("select m from Movie m where m.title like :title", Movie.class)
                            .setParameter("title", "%Mile%")
                            .getSingleResult());

            movie.setReleaseYear(2000); // Dirty checking

            return movie;
        });

        assertThat(persistedMovie.getReleaseYear()).isEqualTo(2000);
    }

    @Test
    void detachedEntity_noDirtyChecking() {
        Movie detachedMovie = transactionTemplateHelper.execute(() ->
                entityManager.merge(MovieFactory.theGreenMile())); // Detached entity

        detachedMovie.setReleaseYear(2000); // No dirty checking

        // Update in DB
        Movie persistedMovie = transactionTemplateHelper.execute(() -> entityManager.merge(detachedMovie));

        assertThat(persistedMovie.getReleaseYear()).isEqualTo(2000);
    }
}
