package com.example.zauth.APIIntefeces;

import com.example.zauth.Model.EmailOtpOptions;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiEmailOtpCreateUserService {
    @POST("/verifyOtpAndAddUserWithEmail")
    Call<String> createUser(@Body EmailOtpOptions data);
}
