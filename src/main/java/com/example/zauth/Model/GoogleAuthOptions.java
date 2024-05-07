package com.example.zauth.Model;

import com.example.zauth.ClientConfig.ClientDetails;

public class GoogleAuthOptions {
    private String email;
    private String googleId;
    private String role;
    private String sessionTime;  //unit->minutes
    private final String clientId= ClientDetails.getClientId();
    private final String clintApi=ClientDetails.getClientApiKey();
    private final String clientApikey=ClientDetails.getClientApiPass();
    private final String platform="null";

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public GoogleAuthOptions setRole(String role) {
        this.role = role;
        return this;
    }

    public GoogleAuthOptions setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getRole() {
        return role;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClintApi() {
        return clintApi;
    }

    public String getClientApikey() {
        return clientApikey;
    }

    public String getPlatform() {
        return platform;
    }
}
