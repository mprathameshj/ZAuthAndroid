package com.example.zauth.APIIntefeces;

import com.example.zauth.Model.PhoneAuthOptions;
import com.example.zauth.Model.SendSMSData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiPhoneAuthService {
    @POST("/verifyotpAndAddUser")
    Call<String> createUser(@Body PhoneAuthOptions data);
}
