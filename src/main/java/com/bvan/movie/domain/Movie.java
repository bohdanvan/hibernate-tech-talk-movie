package com.bvan.movie.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private int releaseYear;

    private Rating rating;

    private BoxOffice boxOffice;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"))
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
//    @Fetch(SELECT)
    private List<Review> reviews = new ArrayList<>();

    public Movie(String title, int releaseYear, Rating rating, Set<Genre> genres) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.genres = genres;
    }

    public Movie addReview(Review review) {
        getReviews().add(review);
        return this;
    }
}
