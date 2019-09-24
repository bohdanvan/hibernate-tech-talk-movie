package com.bvan.movie;

import static com.bvan.movie.domain.Genre.ACTION;
import static com.bvan.movie.domain.Genre.ADVENTURE;
import static com.bvan.movie.domain.Genre.CRIME;
import static com.bvan.movie.domain.Genre.FAMILY;
import static com.bvan.movie.domain.Genre.FANTASY;
import static com.bvan.movie.domain.Genre.SCI_FI;
import static com.bvan.movie.domain.Genre.THRILLER;
import static org.assertj.core.api.Assertions.assertThat;

import com.bvan.movie.annotation.TestContainerDataSourceTest;
import com.bvan.movie.domain.Movie;
import com.bvan.movie.domain.Rating;
import com.bvan.movie.domain.Review;
import com.bvan.movie.util.TransactionTemplateHelper;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@TestContainerDataSourceTest
class ReviewTest {

    private static final Logger logger = LoggerFactory.getLogger(ReviewTest.class);

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private TransactionTemplateHelper transactionTemplateHelper;

    @BeforeEach
    void setUp() {
        logger.info("=============== BEFORE TEST: Start ===============");
        transactionTemplateHelper.executeInNewTransaction(() -> {
            entityManager
                    .merge(new Movie("Avengers: Endgame", 2019, Rating.PG_13, Set.of(ACTION, ADVENTURE, SCI_FI)))
                    .addReview(entityManager.merge(new Review("john", "Good", 7)))
                    .addReview(entityManager.merge(new Review("bill", "Nice", 6)))
                    .addReview(entityManager.merge(new Review("cris", "Excellent", 10)));

            entityManager
                    .merge(new Movie("John Wick 3: Chapter 3 - Parabellum", 2019, Rating.R,
                            Set.of(ACTION, CRIME, THRILLER)))
                    .addReview(entityManager.merge(new Review("john2", "Good 2", 7)))
                    .addReview(entityManager.merge(new Review("bill2", "Nice 2", 6)));

            entityManager
                    .merge(new Movie("Aladdin", 2019, Rating.PG, Set.of(ADVENTURE, FAMILY, FANTASY)));
        });
        logger.info("=============== BEFORE TEST: Finish ===============");
    }

    @Test
    void fetchReviewsCount_nPlusOneProblem() {
        transactionTemplateHelper.executeInNewTransaction(() -> {
            List<Movie> movies = entityManager
                    .createQuery("select m from Movie m", Movie.class)
                    .getResultList();
            // 1 select

            int reviews = 0;
            for (Movie movie : movies) { // n times
                reviews += movie.getReviews().size(); // 1 select
            }
            // Total (n + 1) selects

            assertThat(reviews).isEqualTo(5);
        });
    }

    @Test
    void fetchReviewsCount_joinFetch() {
        transactionTemplateHelper.executeInNewTransaction(() -> {
            List<Movie> movies = entityManager
                    .createQuery("select distinct m from Movie m left join fetch m.reviews", Movie.class)
                    .getResultList();
            // 1 select with join

            int reviews = 0;
            for (Movie movie : movies) {
                reviews += movie.getReviews().size();
            }
            assertThat(reviews).isEqualTo(5);
        });
    }
}
