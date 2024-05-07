package com.example.zauth.APIIntefeces;

import com.example.zauth.Model.UsernamePasswordOption;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiUsernamePassService {
    @POST("/createUserWithUsenameAndPassword")
    Call<Void> createUser(@Body UsernamePasswordOption data);
}
