package com.example.zauth.APIIntefeces;

import com.example.zauth.Model.EmailPasswordOptions;
import com.example.zauth.Model.GoogleAuthOptions;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiGoogleloginService {
    @POST("/androidGoogleSignInOrLogin")
    Call<String> createUser(@Body GoogleAuthOptions data);
}
