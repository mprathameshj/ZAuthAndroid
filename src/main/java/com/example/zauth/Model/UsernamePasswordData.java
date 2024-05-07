package com.example.zauth.Model;

import com.example.zauth.ClientConfig.ClientDetails;

public class UsernamePasswordData {
    private String userName;
    private String password;
    private String clientId= ClientDetails.getClientId();
    private String clientApiKey=ClientDetails.getClientApiKey();
    private String clientApiPass=ClientDetails.getClientApiPass();
    private final String platform="null";

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientApiKey() {
        return clientApiKey;
    }

    public String getClientApiPass() {
        return clientApiPass;
    }

    public String getPlatform() {
        return platform;
    }
}
