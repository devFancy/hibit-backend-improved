package com.hibitbackendrefactor.profile.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    List<ProfileImage> findByProfileId(final Long profileId);

    @Query("SELECT pi FROM ProfileImage pi WHERE pi.profile = :profile")
    List<ProfileImage> findByProfile(@Param("profile") final Profile profile);


    @Modifying(clearAutomatically = true)
    @Query(value = "" +
            "DELETE FROM profile_image " +
            "WHERE profile_id = :profileId", nativeQuery = true)
    void deleteByProfileId(@Param("profileId") final Long profileId);
}
