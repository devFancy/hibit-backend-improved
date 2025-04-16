package com.hibitbackendrefactor.infrastructure.oauth.dto;

public class GoogleUserInfo {

    private String email;
    private String name;


    private GoogleUserInfo() {
    }

    public GoogleUserInfo(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

}
