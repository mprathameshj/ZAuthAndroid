package com.example.zauth.APIIntefeces;

import com.example.zauth.Model.EmailSendOTPData;
import com.example.zauth.Model.SendSMSData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiSendMobileOtpService {
    @POST("/sendotptonumber")
    Call<Void> createUser(@Body SendSMSData data);
}
