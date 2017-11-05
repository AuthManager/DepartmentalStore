package com.departmental.store.api.auth.controller.dto.response;

public class AuthResponse {

    private String authcode;
    private String firstname;

    public AuthResponse(String authcode, String firstname) {
        this.authcode = authcode;
        this.firstname = firstname;
    }

    public String getAuthcode() {
        return authcode;
    }

    public String getFirstname() {
        return firstname;
    }

}
