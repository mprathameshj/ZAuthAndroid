package com.example.zauth.APIIntefeces;
import com.example.zauth.Model.ValidateTokenData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiValidateTokenService {
        @POST("/validateToken")
        Call<Void> validateToken(@Body ValidateTokenData data);
}
