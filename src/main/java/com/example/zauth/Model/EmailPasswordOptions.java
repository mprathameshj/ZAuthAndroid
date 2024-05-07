package com.example.zauth.Model;

import com.example.zauth.ClientConfig.ClientDetails;

public class EmailPasswordOptions {
    private String email;
    private String password;
    private String role;
    private String sessionTime;  //unit->minutes
    private String clientId= ClientDetails.getClientId();
    private String clintApi=ClientDetails.getClientApiKey();
    private String clientApikey=ClientDetails.getClientApiPass();
    private final String platform="null";


    public EmailPasswordOptions setEmail(String email) {
        this.email = email;
        return this;
    }

    public EmailPasswordOptions setPassword(String password) {
        this.password = password;
        return this;
    }

    public EmailPasswordOptions setRole(String role) {
        this.role = role;
        return this;
    }

    public EmailPasswordOptions setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
