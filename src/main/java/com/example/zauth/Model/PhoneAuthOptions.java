package com.example.zauth.Model;

import com.example.zauth.APIIntefeces.ApiEmailOtpService;
import com.example.zauth.APIIntefeces.ApiSendMobileOtpService;
import com.example.zauth.ClientConfig.ClientDetails;
import com.example.zauth.ListnerInterfaces.OtpListner;
import com.example.zauth.ZRetrofit.RetrofitClient;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneAuthOptions {
    private String MobNumber;
    private String Role;
    private String sessionTime;  //unit->minutes
    private String clientId= ClientDetails.getClientId();
    private String clintApi=ClientDetails.getClientApiKey();
    private String clientApikey=ClientDetails.getClientApiPass();
    private final String senderToken= String.valueOf(UUID.randomUUID());
    private String otp;
    private final String platform="null";

    public void sendOtp(OtpListner listner){
        // Create an instance of the Retrofit interface
        ApiSendMobileOtpService apiService = RetrofitClient.getClient().create(ApiSendMobileOtpService.class);
        SendSMSData option=new SendSMSData();
        option.setMobileNumber(this.MobNumber);
        option.setSenderToken(senderToken);
        // Make the POST request
        retrofit2.Call<Void> call = apiService.createUser(option);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                listner.onOtpSent();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                listner.onFail(new Exception(throwable));
            }
        });
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public PhoneAuthOptions setMobNumber(String mobNumber) {
        MobNumber = mobNumber;
        return  this;
    }

    public PhoneAuthOptions setRole(String role) {
        Role = role;
        return this;
    }

    public PhoneAuthOptions setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
         return this;
    }

    public String getMobNumber() {
        return MobNumber;
    }

    public String getRole() {
        return Role;
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

    public String getSenderToken() {
        return senderToken;
    }

    public String getOtp() {
        return otp;
    }

    public String getPlatform() {
        return platform;
    }
}
