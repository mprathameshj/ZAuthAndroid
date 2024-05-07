package com.example.zauth.Model;

import com.example.zauth.ClientConfig.ClientDetails;

public class EmailSendOTPData {
    private String email;
    private String clientId= ClientDetails.getClientId();
    private String clientApi=ClientDetails.getClientApiKey();
    private String clientApiPass=ClientDetails.getClientApiPass();


    public String getEmail() {
        return email;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientApi() {
        return clientApi;
    }

    public String getClientApiPass() {
        return clientApiPass;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
