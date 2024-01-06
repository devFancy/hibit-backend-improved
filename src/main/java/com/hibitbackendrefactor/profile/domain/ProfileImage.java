package com.hibitbackendrefactor.profile.domain;

import com.hibitbackendrefactor.common.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(name = "image_url")
    private String imageUrl;

    public ProfileImage(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProfileImage(final Profile profile, final String imageUrl) {
        this.profile = profile;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
