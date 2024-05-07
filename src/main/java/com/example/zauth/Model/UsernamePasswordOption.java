package com.example.zauth.Model;

import com.example.zauth.ClientConfig.ClientDetails;

public class UsernamePasswordOption {
    private String userName;
    private String password;
    private String role="null";
    private String sessionTime="null";  //unit->minutes
    private String clientId= ClientDetails.getClientId();
    private String clintApi=ClientDetails.getClientApiKey();
    private String clientApikey=ClientDetails.getClientApiPass();
    private String recoveryMail="null";
    private String recoveryMob="null";
    private final String platform="null";


    public UsernamePasswordOption setUsername(String userName){
        this.userName=userName;
        return this;
    }
    public UsernamePasswordOption setPassword(String password){
        this.password=password;
        return this;
    }
    public UsernamePasswordOption setRole(String role){
        this.role=role;
        return this;
    }
    public UsernamePasswordOption setSessionTime(String sessionTime){
        this.sessionTime=sessionTime;
        return this;
    }
    public UsernamePasswordOption setRecoveryMobileNumber(String recoveryMob){
        this.recoveryMob=recoveryMob;
        return this;
    }
    public UsernamePasswordOption setRecoveryEmail(String recoveryMail){
        this.recoveryMail=recoveryMail;
        return this;
    }



    public String getUserName() {
        return userName;
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

    public String getRecoveryMail() {
        return recoveryMail;
    }

    public String getRecoveryMob() {
        return recoveryMob;
    }

    public String getPlatform() {
        return platform;
    }
}
