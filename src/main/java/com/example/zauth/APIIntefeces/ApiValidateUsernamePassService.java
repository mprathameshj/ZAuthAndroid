package com.example.zauth.APIIntefeces;

import com.example.zauth.Model.UsernamePasswordData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiValidateUsernamePassService {
    @POST("/validateUserNamePassword")
    Call<String> createUser(@Body UsernamePasswordData data);
}
