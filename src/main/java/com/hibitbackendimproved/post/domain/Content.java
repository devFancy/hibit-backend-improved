package com.hibitbackendimproved.post.domain;

import com.hibitbackendimproved.post.exception.InvalidContentException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.Objects;

@Getter
@Embeddable
public class Content {

    private static final int MAX_CONTENT_LENGTH = 200;

    @Column(name = "content", nullable = false)
    @Lob
    private String value;

    protected Content() {
    }

    public Content(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidContentException();
        }
        if (value.length() > MAX_CONTENT_LENGTH) {
            throw new InvalidContentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Content)) {
            return false;
        }
        Content content = (Content) o;
        return Objects.equals(value, content.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
