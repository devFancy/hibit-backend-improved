package com.hibitbackendimproved.auth.dto;

public class LoginMember {

    private Long id;

    public LoginMember() {
    }

    public LoginMember(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
