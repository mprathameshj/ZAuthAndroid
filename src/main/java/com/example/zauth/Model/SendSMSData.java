package com.example.zauth.Model;

import com.example.zauth.ClientConfig.ClientDetails;

public class SendSMSData {
    private String mobileNumber;
    private String senderToken;
    private String clientId= ClientDetails.getClientId();
    private String clientApiKey=ClientDetails.getClientApiKey();
    private String clientApiPass=ClientDetails.getClientApiPass();


    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getSenderToken() {
        return senderToken;
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
}
