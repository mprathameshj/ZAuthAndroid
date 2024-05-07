package com.example.zauth.APIIntefeces;

import com.example.zauth.Model.UsernamePasswordOption;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiTest {
    @POST("/testMethod")
    Call<String> createUser( );

}
