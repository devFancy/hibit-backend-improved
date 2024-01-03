package com.hibitbackendrefactor.infrastructure.oauth.dto;

public class UserInfo {

    private String email;
    private String name;


    private UserInfo() {
    }

    public UserInfo(String email, String name) {
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
