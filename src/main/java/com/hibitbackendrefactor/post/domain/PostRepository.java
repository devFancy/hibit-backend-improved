package com.hibitbackendrefactor.post.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * " +
            "FROM posts p " +
            "ORDER BY p.created_date_time DESC", nativeQuery = true)
    List<Post> findAllByOrderByCreatedDateTimeDesc();

    @Query("SELECT p "
            + "FROM Post p "
            + "WHERE p.member.id = :memberId")
    Optional<Post> findByMemberId(@Param("memberId") final Long memberId);

    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :postId")
    void updateViewCount(@Param("postId") Long postId);

    @EntityGraph(attributePaths = "member")
    @Query("SELECT p FROM Post p")
    Optional<Post> findPostById(Long id);
}