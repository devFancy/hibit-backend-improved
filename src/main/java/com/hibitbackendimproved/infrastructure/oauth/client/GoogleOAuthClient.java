package com.hibitbackendimproved.infrastructure.oauth.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibitbackendimproved.auth.application.OAuthClient;
import com.hibitbackendimproved.auth.dto.OAuthMember;
import com.hibitbackendimproved.auth.dto.response.OAuthAccessTokenResponse;
import com.hibitbackendimproved.config.properties.GoogleProperties;
import com.hibitbackendimproved.infrastructure.oauth.dto.GoogleTokenResponse;
import com.hibitbackendimproved.infrastructure.oauth.dto.GoogleUserInfo;
import com.hibitbackendimproved.infrastructure.oauth.exception.OAuthException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class GoogleOAuthClient implements OAuthClient {

    private static final String JWT_DELIMITER = "\\.";

    private final GoogleProperties properties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GoogleOAuthClient(final GoogleProperties properties,
                             final RestTemplateBuilder restTemplateBuilder,
                             final ObjectMapper objectMapper) {
        this.properties = properties;
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public OAuthMember getOAuthMember(final String code, final String redirectUri) {
        GoogleTokenResponse googleTokenResponse = requestGoogleToken(code, redirectUri);
        String payload = getPayload(googleTokenResponse.getIdToken());
        GoogleUserInfo googleUserInfo = parseUserInfo(payload);

        String refreshToken = googleTokenResponse.getRefreshToken();
        return new OAuthMember(googleUserInfo.getEmail(), googleUserInfo.getName(), refreshToken);
    }

    private GoogleTokenResponse requestGoogleToken(final String code, final String redirectUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = generateTokenParams(code, redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        return fetchGoogleToken(request).getBody();
    }

    private MultiValueMap<String, String> generateTokenParams(final String code, final String redirectUri) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", properties.getClientId());
        params.add("client_secret", properties.getClientSecret());
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", redirectUri);
        return params;
    }

    private ResponseEntity<GoogleTokenResponse> fetchGoogleToken(
            final HttpEntity<MultiValueMap<String, String>> request) {
        try {
            return restTemplate.postForEntity(properties.getTokenUri(), request, GoogleTokenResponse.class);
        } catch (final RestClientException e) {
            throw new OAuthException(e);
        }
    }

    private String getPayload(final String jwt) {
        return jwt.split(JWT_DELIMITER)[1];
    }

    private GoogleUserInfo parseUserInfo(final String payload) {
        String decodedPayload = decodeJwtPayload(payload);
        try {
            return objectMapper.readValue(decodedPayload, GoogleUserInfo.class);
        } catch (final JsonProcessingException e) {
            throw new OAuthException("id 토큰을 읽을 수 없습니다.", e);
        }
    }

    private String decodeJwtPayload(final String payload) {
        return new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
    }

    @Override
    public OAuthAccessTokenResponse getAccessToken(final String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = generateAccessTokenParams(refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        return fetchGoogleAccessToken(request).getBody();
    }

    private MultiValueMap<String, String> generateAccessTokenParams(final String refreshToken) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", properties.getClientId());
        params.add("client_secret", properties.getClientSecret());
        params.add("refresh_token", refreshToken);
        params.add("grant_type", "refresh_token");
        return params;
    }

    private ResponseEntity<OAuthAccessTokenResponse> fetchGoogleAccessToken(
            final HttpEntity<MultiValueMap<String, String>> request) {
        try {
            return restTemplate.postForEntity(properties.getTokenUri(), request, OAuthAccessTokenResponse.class);
        } catch (final RestClientException e) {
            throw new OAuthException(e);
        }
    }
}
