package com.bvan.movie.repository;

import com.bvan.movie.domain.Movie;
import com.bvan.movie.domain.MovieView;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    Movie findMovieByTitleContaining(String title);

    @Query("select new com.bvan.movie.domain.MovieView(m.title, m.releaseYear) from Movie m")
    List<MovieView> findAllMovieViews();
}
