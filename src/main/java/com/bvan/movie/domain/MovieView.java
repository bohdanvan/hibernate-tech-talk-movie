package com.bvan.movie.domain;

import java.util.Objects;

public class MovieView {

    private final String title;
    private final int releaseYear;

    public MovieView(String title, int releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieView movieView = (MovieView) o;
        return releaseYear == movieView.releaseYear &&
                Objects.equals(title, movieView.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, releaseYear);
    }

    @Override
    public String toString() {
        return "MovieView{" +
                "title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }
}
