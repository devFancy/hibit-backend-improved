package com.hibitbackendimproved.auth.domain;

import com.hibitbackendimproved.common.BaseEntity;
import com.hibitbackendimproved.member.domain.Member;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "oauth_tokens")
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
