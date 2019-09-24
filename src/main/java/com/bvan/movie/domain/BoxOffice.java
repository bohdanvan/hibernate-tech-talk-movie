package com.bvan.movie.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Access(AccessType.FIELD)
@Data
public class BoxOffice { // Will be a part of the 'movie' table

    private Long budget;
    private Long openingWeekendUsa;

    public BoxOffice() {
    }

    public BoxOffice(Long budget, Long openingWeekendUsa) {
        this.budget = budget;
        this.openingWeekendUsa = openingWeekendUsa;
    }
}
