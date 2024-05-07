package com.example.zauth.APIIntefeces;

import com.example.zauth.Model.EmailPasswordOptions;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiEmailAndPassService {
    @POST("/createUserWithEmailPass")
    Call<String> createUser(@Body EmailPasswordOptions data);
}
