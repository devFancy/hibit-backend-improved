package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.post.exception.NotFoundPostException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    default Post getById(final Long id) {
        return findById(id)
                .orElseThrow(NotFoundPostException::new);
    }
}
