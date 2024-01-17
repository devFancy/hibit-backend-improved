package com.hibitbackendrefactor.common.builder;

import com.hibitbackendrefactor.auth.domain.OAuthToken;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.SocialType;

public class GivenBuilder {
    private final BuilderSupporter bs;

    private Member member;

    public GivenBuilder(BuilderSupporter bs) {
        this.bs = bs;
    }

    public GivenBuilder 회원_가입을_한다(final String email, final String displayName) {
        Member member = new Member(email, displayName, SocialType.GOOGLE);
        this.member = bs.memberRepository().save(member);
        OAuthToken oAuthToken = new OAuthToken(this.member, "refreshTokenValue");
        bs.oAuthTokenRepository().save(oAuthToken);
        return this;
    }

    public Member 회원() {
        return member;
    }
}
