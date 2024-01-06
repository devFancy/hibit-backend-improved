package com.hibitbackendrefactor.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    @Query(value = "SELECT pi.image_url " +
            "FROM post_image pi " +
            "WHERE pi.post_id = :postId limit 1", nativeQuery = true)

    String findOneImageUrlByPostId(@Param("postId") final Long postId);

    @Query(value = "SELECT pi.imageUrl " +
                    "FROM PostImage pi " +
                    "WHERE pi.post.id = :postId ")
    List<String> findAllImageUrlsByPostId(@Param("postId") final Long id);
}
