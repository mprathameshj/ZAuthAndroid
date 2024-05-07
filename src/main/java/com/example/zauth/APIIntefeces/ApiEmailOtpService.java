package com.example.zauth.APIIntefeces;

import com.example.zauth.Model.EmailSendOTPData;
import com.example.zauth.Model.UsernamePasswordOption;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiEmailOtpService {
    @POST("/sendOTPToEmail")
    Call<Void> createUser(@Body EmailSendOTPData data);
}
