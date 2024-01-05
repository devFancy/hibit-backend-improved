package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.post.exception.NotFoundPostException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    default Post getById(final Long id) {
        return findById(id)
                .orElseThrow(NotFoundPostException::new);
    }

    @Query(value = "SELECT * " +
            "FROM post p " +
            "ORDER BY p.created_date_time DESC", nativeQuery = true)
    List<Post> findAllByOrderByCreatedDateTimeDesc(final Pageable pageable);
}
