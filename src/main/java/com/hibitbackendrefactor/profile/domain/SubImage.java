package com.hibitbackendrefactor.profile.domain;

import com.hibitbackendrefactor.common.BaseEntity;

import javax.persistence.*;

@Entity
public class SubImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private String imageUrl;

    protected SubImage() {
    }

    public SubImage(Profile profile, String imageUrl) {
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
