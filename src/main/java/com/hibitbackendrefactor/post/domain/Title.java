package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.post.exception.InvalidTitleException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public class Title {

    private static final int MAX_TITLE_LENGTH = 30;

    @Column(name = "title", nullable = false)
    private String value;

    protected Title() {
    }

    public Title(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidTitleException();
        }
        if (value.length() > MAX_TITLE_LENGTH) {
            throw new InvalidTitleException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Title)) {
            return false;
        }
        Title title = (Title) o;
        return Objects.equals(value, title.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
