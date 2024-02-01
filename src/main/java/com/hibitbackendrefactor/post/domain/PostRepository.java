package com.hibitbackendrefactor.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    String SEARCH_SQL = "select * from posts where "
            + "(:query is null or :query = '') "
            + "or "
            + "(title regexp :query) "
            + "or "
            + "(content regexp :query) ";

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
    void updateViewCount(@Param("postId") final Long postId);

    @EntityGraph(attributePaths = "member")
    @Query("SELECT p FROM Post p WHERE p.id = :postId")
    List<Post> findPostById(@Param("postId") final Long postId);

    @Query(value = SEARCH_SQL, nativeQuery = true)
    Page<Post> findPostPagesByQuery(Pageable pageable, @Param("query") String query);

    @Query(value = SEARCH_SQL, nativeQuery = true)
    Page<Post> findPostSlicePageByQuery(Pageable pageable, @Param("query") String query);
}