package com.example.zauth.Model;

import android.content.Context;
import android.provider.ContactsContract;

import com.example.zauth.AuthData.AuthCred;
import com.example.zauth.AuthData.UserInfo;
import com.example.zauth.ClientConfig.ClientDetails;

public class ValidateTokenData {
    private final String clientId= ClientDetails.getClientId();
    private String userId;
    private String authToken;
    private final String platform="null";

    public ValidateTokenData(){

    }

    public String getClientId() {
        return clientId;
    }

    public String getUserId() {
        return userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPlatform() {
        return platform;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
