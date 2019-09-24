package com.bvan.movie.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String author;

    @Column(length = 500)
    private String content;

    @Min(1)
    @Max(10)
    private int rating;

    public Review(
            @NotNull String author,
            String content,
            @Min(1) @Max(10) int rating) {
        this.author = author;
        this.content = content;
        this.rating = rating;
    }
}
