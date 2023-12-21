package com.hibitbackendrefactor.auth.application;



@FunctionalInterface
public interface OAuthUri {
    String generate(final String redirectUri);
}
