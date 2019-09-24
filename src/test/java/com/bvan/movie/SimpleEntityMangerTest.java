package com.bvan.movie;

import static org.assertj.core.api.Assertions.assertThat;

import com.bvan.movie.annotation.TestContainerDataSourceTest;
import com.bvan.movie.domain.Movie;
import com.bvan.movie.domain.MovieView;
import com.bvan.movie.domain.Rating;
import com.bvan.movie.smoke.Greeting;
import com.bvan.movie.util.TransactionTemplateHelper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@TestContainerDataSourceTest
class SimpleEntityMangerTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplateHelper transactionTemplateHelper;

    @Test
    void persistAndFind() {
        transactionTemplateHelper.execute(() -> {
            Movie movie = MovieFactory.theGreenMile();
            entityManager.persist(movie);
            // No flush

            Movie persistedMovie = entityManager.find(Movie.class, movie.getId());
            assertThat(persistedMovie.getTitle()).isEqualTo("The Green Mile");
        });
    }

    @Test
    void persistAndFindByJpql() {
        transactionTemplateHelper.execute(() -> {
            entityManager.persist(MovieFactory.theGreenMile());
            // Flush before select

            Movie persistedMovie = entityManager
                    .createQuery("select m from Movie m where m.title like :title", Movie.class)
                    .setParameter("title", "%Mile%")
                    .getSingleResult();
            assertThat(persistedMovie.getTitle()).isEqualTo("The Green Mile");
        });
    }

    @Test
    void persistAndFindOtherEntity() {
        transactionTemplateHelper.execute(() -> {
            entityManager.persist(MovieFactory.theGreenMile());
            // No flush before select of the other entity

            List<Greeting> greetings = entityManager
                    .createQuery("select g from Greeting g", Greeting.class)
                    .getResultList();
        });
    }

    @Test
    void persistAndFindOtherEntityUsingNativeQuery() {
        transactionTemplateHelper.execute(() -> {
            entityManager.persist(MovieFactory.theGreenMile());
            // Flush before native query

            List<Greeting> greetings = entityManager
                    .createNativeQuery("select * from greeting", Greeting.class)
                    .getResultList();
            System.out.println();
        });
    }

    @Test
    void findViews() {
        transactionTemplateHelper.execute(() -> {
            entityManager.persist(MovieFactory.theGreenMile());
            entityManager.persist(MovieFactory.superman());

            List<MovieView> movieViews = entityManager
                    .createQuery("select new com.bvan.movie.domain.MovieView(m.title, m.releaseYear) from Movie m",
                            MovieView.class)
                    .getResultList();

            assertThat(movieViews).contains(
                    new MovieView("The Green Mile", 1999),
                    new MovieView("Superman", 1978)
            );
        });
    }
}
