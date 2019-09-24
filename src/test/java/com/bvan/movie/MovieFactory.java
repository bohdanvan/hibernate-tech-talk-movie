package com.bvan.movie;

import static com.bvan.movie.domain.Genre.ACTION;
import static com.bvan.movie.domain.Genre.ADVENTURE;
import static com.bvan.movie.domain.Genre.CRIME;
import static com.bvan.movie.domain.Genre.DRAMA;
import static com.bvan.movie.domain.Genre.FANTASY;

import com.bvan.movie.domain.BoxOffice;
import com.bvan.movie.domain.Movie;
import com.bvan.movie.domain.Rating;
import java.util.Set;

public class MovieFactory {

    private MovieFactory() {}

    public static Movie theGreenMile() {
        return new Movie("The Green Mile", 1999, Rating.R, Set.of(CRIME, DRAMA, FANTASY))
                .setBoxOffice(new BoxOffice(60_000_000L, 18_017_152L));
    }

    public static Movie superman() {
        return new Movie("Superman", 1978, Rating.PG, Set.of(ACTION, ADVENTURE, DRAMA));
    }
}
