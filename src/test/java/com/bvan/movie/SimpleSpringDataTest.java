package com.bvan.movie;

import static org.assertj.core.api.Assertions.assertThat;

import com.bvan.movie.annotation.TestContainerDataSourceTest;
import com.bvan.movie.domain.Movie;
import com.bvan.movie.domain.MovieView;
import com.bvan.movie.repository.MovieRepository;
import com.bvan.movie.util.TransactionTemplateHelper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@TestContainerDataSourceTest
class SimpleSpringDataTest {

    private final MovieRepository movieRepository;
    private final TransactionTemplateHelper transactionTemplateHelper;

    @Autowired
    SimpleSpringDataTest(
            MovieRepository movieRepository,
            TransactionTemplateHelper transactionTemplateHelper) {
        this.movieRepository = movieRepository;
        this.transactionTemplateHelper = transactionTemplateHelper;
    }

    @Test
    void saveAndFind() {
        transactionTemplateHelper.execute(() -> {
            movieRepository.save(MovieFactory.theGreenMile());

            Movie persistedMovie = movieRepository.findMovieByTitleContaining("Mile");
            assertThat(persistedMovie.getTitle()).isEqualTo("The Green Mile");
        });
    }

    @Test
    void saveAndFindByTitle() {
        transactionTemplateHelper.execute(() -> {
            movieRepository.save(MovieFactory.theGreenMile());

            Movie persistedMovie = movieRepository.findMovieByTitleContaining("Mile");
            assertThat(persistedMovie.getTitle()).isEqualTo("The Green Mile");
        });
    }

    @Test
    void findViews() {
        transactionTemplateHelper.execute(() -> {
            movieRepository.save(MovieFactory.theGreenMile());
            movieRepository.save(MovieFactory.superman());

            List<MovieView> movieViews = movieRepository.findAllMovieViews();

            assertThat(movieViews).contains(
                    new MovieView("The Green Mile", 1999),
                    new MovieView("Superman", 1978)
            );
        });
    }
}
