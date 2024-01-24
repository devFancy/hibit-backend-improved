package com.hibitbackendrefactor.auth.domain;

import com.hibitbackendrefactor.common.BaseEntity;
import com.hibitbackendrefactor.member.domain.Member;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class OAuthToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id", nullable = false)
    private Member member;
    @Column(name = "refresh_token")
    private String refreshToken;

    protected OAuthToken() {
    }

    public OAuthToken(final Member member, final String refreshToken) {
        this.member = member;
        this.refreshToken = refreshToken;
    }

    public void change(final String refreshToken) {
        if (!Objects.isNull(refreshToken)) {
            this.refreshToken = refreshToken;
        }
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
