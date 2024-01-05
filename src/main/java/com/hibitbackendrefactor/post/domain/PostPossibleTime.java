package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Embeddable
public class PostPossibleTime {

    private LocalDate possibleDate;

    @Enumerated(EnumType.STRING)
    private DayHalf dayHalf;

    protected PostPossibleTime() {
    }

    public PostPossibleTime(final LocalDate possibleDate, final DayHalf dayHalf) {
        this.possibleDate = possibleDate;
        this.dayHalf = dayHalf;
    }

    public LocalDate getPossibleDate() {
        return possibleDate;
    }

    public DayHalf getDayHalf() {
        return dayHalf;
    }
}
