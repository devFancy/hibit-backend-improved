package com.hibitbackendrefactor.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hibitbackendrefactor.member.exception.NotFoundMemberException;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);

    default Member getById(final Long id) {
        return findById(id)
                .orElseThrow(NotFoundMemberException::new);
    }

    boolean existsByEmail(final String email);

    default Member getByEmail(final String email) {
        return findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);
    }

    default void validateExistById(final Long id) {
        if (!existsById(id)) {
            throw new NotFoundMemberException();
        }
    }

}