package com.hibitbackendimproved.common.builder;

import com.hibitbackendimproved.auth.domain.OAuthTokenRepository;
import com.hibitbackendimproved.member.domain.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class BuilderSupporter {

    private final MemberRepository memberRepository;

    private final OAuthTokenRepository oAuthTokenRepository;

    public BuilderSupporter(final MemberRepository memberRepository,
                            final OAuthTokenRepository oAuthTokenRepository) {
        this.memberRepository = memberRepository;
        this.oAuthTokenRepository = oAuthTokenRepository;
    }

    public MemberRepository memberRepository() {
        return memberRepository;
    }

    public OAuthTokenRepository oAuthTokenRepository() {
        return oAuthTokenRepository;
    }
}
