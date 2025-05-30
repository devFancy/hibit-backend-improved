package com.hibitbackendimproved.auth.application;

import com.hibitbackendimproved.auth.domain.AuthToken;
import com.hibitbackendimproved.auth.domain.OAuthToken;
import com.hibitbackendimproved.auth.domain.OAuthTokenRepository;
import com.hibitbackendimproved.auth.dto.OAuthMember;
import com.hibitbackendimproved.auth.dto.request.TokenRenewalRequest;
import com.hibitbackendimproved.auth.dto.response.AccessAndRefreshTokenResponse;
import com.hibitbackendimproved.auth.dto.response.AccessTokenResponse;
import com.hibitbackendimproved.auth.event.MemberSavedEvent;
import com.hibitbackendimproved.member.domain.Member;
import com.hibitbackendimproved.member.domain.MemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {


    private final MemberRepository memberRepository;
    private final OAuthTokenRepository oAuthTokenRepository;
    private final TokenCreator tokenCreator;
    private final ApplicationEventPublisher eventPublisher;


    public AuthService(final MemberRepository memberRepository, final OAuthTokenRepository oAuthTokenRepository,
                       final TokenCreator tokenCreator, final ApplicationEventPublisher eventPublisher) {
        this.memberRepository = memberRepository;
        this.oAuthTokenRepository = oAuthTokenRepository;
        this.tokenCreator = tokenCreator;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public AccessAndRefreshTokenResponse generateAccessAndRefreshToken(final OAuthMember oAuthMember) {
        Member foundMember = findMember(oAuthMember);

        OAuthToken oAuthToken = getOAuthToken(oAuthMember, foundMember);
        oAuthToken.change(oAuthMember.getRefreshToken());

        AuthToken authToken = tokenCreator.createAuthToken(foundMember.getId());

        return new AccessAndRefreshTokenResponse(authToken.getAccessToken(), authToken.getRefreshToken(), authToken.getIsProfileRegistered());
    }

    private OAuthToken getOAuthToken(final OAuthMember oAuthMember, final Member member) {
        Long memberId = member.getId();
        if (oAuthTokenRepository.existsByMemberId(memberId)) {
            return oAuthTokenRepository.getByMemberId(memberId);
        }
        return oAuthTokenRepository.save(new OAuthToken(member, oAuthMember.getRefreshToken()));
    }

    private Member findMember(final OAuthMember oAuthMember) {
        String email = oAuthMember.getEmail();
        if (memberRepository.existsByEmail(email)) {
            return memberRepository.getByEmail(email);
        }
        return saveMember(oAuthMember);
    }

    private Member saveMember(final OAuthMember oAuthMember) {
        Member savedMember = memberRepository.save(oAuthMember.toMember());
        eventPublisher.publishEvent(new MemberSavedEvent(savedMember.getId()));
        return savedMember;
    }

    public AccessTokenResponse generateAccessToken(final TokenRenewalRequest tokenRenewalRequest) {
        String refreshToken = tokenRenewalRequest.getRefreshToken();
        AuthToken authToken = tokenCreator.renewAuthToken(refreshToken);
        return new AccessTokenResponse(authToken.getAccessToken());
    }

    public Long extractMemberId(final String accessToken) {
        Long memberId = tokenCreator.extractPayLoad(accessToken);
        memberRepository.validateExistById(memberId);
        return memberId;
    }

    @Transactional
    public void deleteToken(final Long id) {
        oAuthTokenRepository.deleteAllByMemberId(id);
    }
}
