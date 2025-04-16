package com.hibitbackendimproved.auth.application;

import com.hibitbackendimproved.auth.domain.AuthToken;
import com.hibitbackendimproved.auth.domain.TokenRepository;
import com.hibitbackendimproved.profile.domain.Profile;
import com.hibitbackendimproved.profile.domain.ProfileRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthTokenCreator implements TokenCreator {
    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;
    private final AuthTokenResponseHandler authTokenResponseHandler;
    private final ProfileRepository profileRepository;

    public AuthTokenCreator(final TokenProvider tokenProvider, final TokenRepository tokenRepository
            , final AuthTokenResponseHandler authTokenResponseHandler, ProfileRepository profileRepository) {
        this.tokenProvider = tokenProvider;
        this.tokenRepository = tokenRepository;
        this.authTokenResponseHandler = authTokenResponseHandler;
        this.profileRepository = profileRepository;
    }

    public AuthToken createAuthToken(final Long memberId) {
        String accessToken = tokenProvider.createAccessToken(String.valueOf(memberId));
        String refreshToken = createRefreshToken(memberId);
        int isProfileRegistered = isProfileRegistered(memberId);

        // 클라이언트로 리프레시 토큰 값을 전달하는 부분
        authTokenResponseHandler.setRefreshTokenCookie(refreshToken);
        return new AuthToken(accessToken, refreshToken, isProfileRegistered);
    }

    private String createRefreshToken(final Long memberId) {
        if (tokenRepository.exist(memberId)) {
            return tokenRepository.getToken(memberId);
        }
        String refreshToken = tokenProvider.createRefreshToken(String.valueOf(memberId));
        return tokenRepository.save(memberId, refreshToken);
    }

    private int isProfileRegistered(final Long memberId) {
        // 데이터베이스에서 memberId를 이용하여 회원의 프로필 정보를 조회
        // 조회한 프로필 정보가 존재하면 true를 반환, 없으면 false를 반환
        Optional<Profile> profile = profileRepository.findByMemberId(memberId);
        return profile.isPresent() ? 1 : 0;
    }

    public AuthToken renewAuthToken(final String refreshToken) {
        tokenProvider.validateToken(refreshToken);
        Long memberId = Long.valueOf(tokenProvider.getPayload(refreshToken));

        String accessTokenForRenew = tokenProvider.createAccessToken(String.valueOf(memberId));
        String refreshTokenForRenew = tokenRepository.getToken(memberId);
        int isProfileRegistered = isProfileRegistered(memberId);

        // 클라이언트로 리프레시 토큰 값을 전달하는 부분
        authTokenResponseHandler.setRefreshTokenCookie(refreshTokenForRenew);

        AuthToken renewalAuthAccessToken = new AuthToken(accessTokenForRenew, refreshTokenForRenew, isProfileRegistered);
        renewalAuthAccessToken.validateHasSameRefreshToken(refreshToken);
        return renewalAuthAccessToken;
    }

    public Long extractPayLoad(final String accessToken) {
        tokenProvider.validateToken(accessToken);
        return Long.valueOf(tokenProvider.getPayload(accessToken));
    }
}
