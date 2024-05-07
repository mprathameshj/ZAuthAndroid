package com.example.zauth.Model;

import android.annotation.SuppressLint;

import com.example.zauth.APIIntefeces.ApiEmailOtpService;
import com.example.zauth.APIIntefeces.ApiUsernamePassService;
import com.example.zauth.ClientConfig.ClientDetails;
import com.example.zauth.ListnerInterfaces.OtpListner;
import com.example.zauth.ZRetrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailOtpOptions {
    private String email;
    private String otp;
    private String role;
    private String sessionTime;  //unit->minutes
    private String clientId= ClientDetails.getClientId();
    private String clintApi=ClientDetails.getClientApiKey();
    private String clientApikey=ClientDetails.getClientApiPass();
    private final String platform="null";


    public void sendOtp(OtpListner listner){
        // Create an instance of the Retrofit interface
        ApiEmailOtpService apiService = RetrofitClient.getClient().create(ApiEmailOtpService.class);

        EmailSendOTPData option=new EmailSendOTPData();
        option.setEmail(this.email);
        // Make the POST request
        retrofit2.Call<Void> call = apiService.createUser(option);

        call.enqueue(new Callback<Void>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                listner.onOtpSent();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                listner.onFail(new Exception(throwable));
            }
        });

    }

    public EmailOtpOptions setEmail(String email){
        this.email=email;
        return this;
    }

    public EmailOtpOptions setOtp(String otp){
        this.otp=otp;
        return this;
    }
    public EmailOtpOptions setRole(String role){
        this.role=role;
        return this;
    }
    public EmailOtpOptions setSessionTime(String sessionTime){
        this.sessionTime=sessionTime;
        return this;
    }



    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
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
