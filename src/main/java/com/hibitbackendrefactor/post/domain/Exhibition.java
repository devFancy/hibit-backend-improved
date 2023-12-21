package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.post.exception.InvalidExhibitionException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.Objects;

@Getter
@Embeddable
public class Exhibition {
    private static final int MAX_EXHIBITION_LENGTH = 50;

    @Column(name = "exhibition", nullable = false)
    @Lob
    private String value;

    protected Exhibition() {
    }

    public Exhibition(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidExhibitionException();
        }
        if (value.length() > MAX_EXHIBITION_LENGTH) {
            throw new InvalidExhibitionException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exhibition)) {
            return false;
        }
        Exhibition exhibition = (Exhibition) o;
        return Objects.equals(value, exhibition.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
