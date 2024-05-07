package com.example.zauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.example.zauth.APIIntefeces.ApiEmailAndPassService;
import com.example.zauth.APIIntefeces.ApiEmailOtpCreateUserService;
import com.example.zauth.APIIntefeces.ApiGoogleloginService;
import com.example.zauth.APIIntefeces.ApiPhoneAuthService;
import com.example.zauth.APIIntefeces.ApiUsernamePassService;
import com.example.zauth.APIIntefeces.ApiValidateTokenService;
import com.example.zauth.APIIntefeces.ApiValidateUsernamePassService;
import com.example.zauth.AuthData.AuthCred;
import com.example.zauth.AuthData.UserInfo;
import com.example.zauth.Data.ZAuthUser;
import com.example.zauth.ListnerInterfaces.AuthListner;
import com.example.zauth.Model.EmailOtpOptions;
import com.example.zauth.Model.EmailPasswordOptions;
import com.example.zauth.Model.GoogleAuthOptions;
import com.example.zauth.Model.PhoneAuthOptions;
import com.example.zauth.Model.UsernamePasswordData;
import com.example.zauth.Model.UsernamePasswordOption;
import com.example.zauth.Model.ValidateTokenData;
import com.example.zauth.ZRetrofit.RetrofitClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZAuth {
    private static Gson gson = new Gson();
    private static ZAuth zAuth;
    private static Context context;
    public static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleSignInManager";
    private GoogleSignInClient mGoogleSignInClient;
    private AuthListner googleAuthListner;
    private GoogleAuthOptions options;


    private ZAuth(){}
    public static synchronized ZAuth getInstance(Context context1){
        if(zAuth==null)zAuth=new ZAuth();
        context=context1;
        return zAuth;
    }

    public  void createUserWithUsernamePassword(UsernamePasswordOption option, AuthListner listner){
        // Create an instance of the Retrofit interface
        ApiUsernamePassService apiService = RetrofitClient.getClient().create(ApiUsernamePassService.class);

        // Make the POST request
        retrofit2.Call<Void> call = apiService.createUser(option);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    listner.onSuccess();
                    String json = String.valueOf(response.body()); // Assuming server sends UserInfo as JSON string
                    if (json != null) {
                        UserInfo userInfo = gson.fromJson(json, UserInfo.class);
                        AuthCred.getInstance(context).saveCredentials(userInfo.getUserId(), userInfo.getAuthToken());
                    }
                }else{
                    int statusCode= response.code();

                    if(statusCode==401) listner.onFail(new InvalidParameterException("The client details are invalid"),1001);
                    else if(statusCode==409) listner.onFail(new Exception("This username is already used"),1002);

                }
            }

            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                listner.onFail(new Exception(t),1003);
            }
        });
    }


    public void loginWithUsernamePassword(String username, String passsword, AuthListner listner){
        // Create an instance of the Retrofit interface
        ApiValidateUsernamePassService apiService = RetrofitClient.getClient().create(ApiValidateUsernamePassService.class);
        UsernamePasswordData option=new UsernamePasswordData();
        option.setUserName(username);
        option.setPassword(passsword);
        // Make the POST request
        Call<String> call = apiService.createUser(option);


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    listner.onSuccess();
                    try {
                        String json = String.valueOf(response.body()); // Assuming server sends UserInfo as JSON string
                        Toast.makeText(context,"the json data "+json, Toast.LENGTH_SHORT).show();
                        if (json != null) {
                            UserInfo userInfo = gson.fromJson(json, UserInfo.class);
                            Toast.makeText(context,userInfo.getUserId()+" user id", Toast.LENGTH_SHORT).show();
                            AuthCred.getInstance(context).saveCredentials(userInfo.getUserId(), userInfo.getAuthToken());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                  String error=response.errorBody().string();
                    int statusCode= response.code();
                    if(statusCode==401) listner.onFail(new InvalidParameterException("The client details are invalid"),1001);
                    else if(statusCode==500) listner.onFail(new Exception(error),1004);
                    else if(statusCode==404) listner.onFail(new Exception("User not found or the username is incorrect"),1005);
                    else if(statusCode==400) listner.onFail(new Exception("Incorrect password"),1006);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void creteOrLoginUserWithEmailAndOtp(EmailOtpOptions options,AuthListner listner){
        // Create an instance of the Retrofit interface
        ApiEmailOtpCreateUserService apiService = RetrofitClient.getClient().create(ApiEmailOtpCreateUserService.class);
        Call<String> call = apiService.createUser(options);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    listner.onSuccess();
                    String json = String.valueOf(response.body()); // Assuming server sends UserInfo as JSON string
                    Toast.makeText(context,"the json data "+json, Toast.LENGTH_SHORT).show();
                    if (json != null) {
                        UserInfo userInfo = gson.fromJson(json, UserInfo.class);
                        Toast.makeText(context,userInfo.getUserId()+" user id", Toast.LENGTH_SHORT).show();
                        AuthCred.getInstance(context).saveCredentials(userInfo.getUserId(), userInfo.getAuthToken());

                    }
                }else{
                    String error;
                    try {
                        error = response.errorBody().string();
                        int statusCode= response.code();
                        if(statusCode==401) listner.onFail(new InvalidParameterException("The client details are invalid"),1001);
                        else if(statusCode==500) listner.onFail(new Exception(error),1004);
                        else if(statusCode==410) listner.onFail(new Exception("The otp has been expired"),1007);
                        else if(statusCode==400) listner.onFail(new Exception("The otp is wrong"),1008);
                        else if(statusCode==423) listner.onFail(new Exception("The user is blocked"),1009);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                listner.onFail(new Exception(throwable),1003);
            }
        });
    }


    public void loginOrSignUpUsingPhoneAuthOptions(PhoneAuthOptions options,String otp,AuthListner listner){
        options.setOtp(otp);
        // Create an instance of the Retrofit interface
        ApiPhoneAuthService apiService = RetrofitClient.getClient().create(ApiPhoneAuthService.class);
        Call<String> call = apiService.createUser(options);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String > response) {
                if(response.isSuccessful()){
                    listner.onSuccess();
                    String json = String.valueOf(response.body()); // Assuming server sends UserInfo as JSON string
                    Toast.makeText(context,"the json data "+json, Toast.LENGTH_SHORT).show();
                    if (json != null) {
                        UserInfo userInfo = gson.fromJson(json, UserInfo.class);
                        Toast.makeText(context,userInfo.getUserId()+" user id", Toast.LENGTH_SHORT).show();
                        AuthCred.getInstance(context).saveCredentials(userInfo.getUserId(), userInfo.getAuthToken());

                    }
                }else{
                    String error;
                    try {
                        error = response.errorBody().string();
                        int statusCode= response.code();
                        if(statusCode==401) listner.onFail(new InvalidParameterException("The client details are invalid"),1001);
                        else if(statusCode==500) listner.onFail(new Exception(error),1004);
                        else if(statusCode==404) listner.onFail(new Exception("The otp has been expired"),1007);
                        else if(statusCode==400) listner.onFail(new Exception("The otp is wrong"),1008);
                        else if(statusCode==406) listner.onFail(new Exception("The user is blocked"),1009);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                listner.onFail(new Exception(throwable),1003);
            }
        });
    }


    public void signInOrLoginWithEmailPasswordOptions(EmailPasswordOptions options,AuthListner listner){
        ApiEmailAndPassService apiService = RetrofitClient.getClient().create(ApiEmailAndPassService.class);
        Call<String> call = apiService.createUser(options);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    listner.onSuccess();
                    try {
                        String json = String.valueOf(response.body()); // Assuming server sends UserInfo as JSON string
                        Toast.makeText(context,"the json data "+json, Toast.LENGTH_SHORT).show();
                        if (json != null) {
                            UserInfo userInfo = gson.fromJson(json, UserInfo.class);
                            Toast.makeText(context,userInfo.getUserId()+" user id", Toast.LENGTH_SHORT).show();
                            AuthCred.getInstance(context).saveCredentials(userInfo.getUserId(), userInfo.getAuthToken());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        String error=response.errorBody().string();
                        int statusCode= response.code();
                        if(statusCode==401) listner.onFail(new InvalidParameterException("The client details are invalid"),1001);
                        else if(statusCode==500) listner.onFail(new Exception(error),1004);
                        else if(statusCode==406) listner.onFail(new Exception("wrong password"),1006);
                        else if(statusCode==400) listner.onFail(new Exception("User is blocked"),1009);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

    public void loginOrSignUpUsingGoogle(Context context, Activity activity, GoogleAuthOptions options1, AuthListner listner) {
        try {
            options=options1;
            googleAuthListner=listner;
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestId()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
            mGoogleSignInClient.signOut();

            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            activity.startActivityForResult(signInIntent, RC_SIGN_IN);
        }catch (Exception e){
            Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void handleSignInResult(@Nullable Intent data) {
        try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult(com.google.android.gms.common.api.ApiException.class);

            options.setEmail(account.getEmail());
            options.setGoogleId(account.getId());

            ApiGoogleloginService apiService = RetrofitClient.getClient().create(ApiGoogleloginService.class);
            Call<String> call = apiService.createUser(options);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        googleAuthListner.onSuccess();
                        try {
                            String json = String.valueOf(response.body()); // Assuming server sends UserInfo as JSON string
                            Toast.makeText(context,"the json data "+json, Toast.LENGTH_SHORT).show();
                            if (json != null) {
                                UserInfo userInfo = gson.fromJson(json, UserInfo.class);
                                Toast.makeText(context,userInfo.getUserId()+" user id", Toast.LENGTH_SHORT).show();
                                AuthCred.getInstance(context).saveCredentials(userInfo.getUserId(), userInfo.getAuthToken());
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        try {
                            String error=response.errorBody().string();
                            int statusCode= response.code();
                            if(statusCode==401) googleAuthListner.onFail(new InvalidParameterException("The client details are invalid"),1001);
                            else if(statusCode==500) googleAuthListner.onFail(new Exception(error),1004);
                            else if(statusCode==400) googleAuthListner.onFail(new Exception("User is blocked"),1009);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {

                }
            });


        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public ZAuthUser getCurrentUser(){
        final CountDownLatch latch = new CountDownLatch(1);
        ValidateTokenData data=new ValidateTokenData();
        UserInfo info=AuthCred.getInstance(context).getCredentials();
        if(info==null) return null;

        data.setUserId(info.getUserId());
        data.setAuthToken(info.getAuthToken());

        final ZAuthUser[] user = {null}; // Using an array to hold the user object

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                // Create an instance of the Retrofit interface
                ApiValidateTokenService apiService = RetrofitClient.getClient().create(ApiValidateTokenService.class);

                // Make the POST request synchronously
                try {


                    retrofit2.Response<Void> response = apiService.validateToken(data).execute();
                    if (response.isSuccessful()) {
                        user[0] = new ZAuthUser();
                        user[0].setUserId(AuthCred.getInstance(context).getCurrentUserId());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                latch.countDown(); // Signal that the operation is complete
                return null;
            }
        }.execute();

        try {
            latch.await(); // Wait for the operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return user[0]; // Return the user object obtained from the background task
    }

    public void signOut(){

        AuthCred.getInstance(context).deleteCredentials();
    }


    public interface OnSignInResultListener {
        void onSignInSuccess(GoogleSignInAccount account);
        void onSignInFailure(Exception exception);
    }


//    public ZAuthUser getCurrentUser(){
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Future<ZAuthUser> future = executor.submit(new Callable<ZAuthUser>() {
//            @Override
//            public ZAuthUser call() throws Exception {
//                // Create an instance of the Retrofit interface
//                ApiValidateTokenService apiService = RetrofitClient.getClient().create(ApiValidateTokenService.class);
//
//                // Make the POST request synchronously
//                try {
//                    retrofit2.Response<Void> response = apiService.validateToken(new ValidateTokenData(context)).execute();
//                    if (response.isSuccessful()) {
//                        ZAuthUser user = new ZAuthUser();
//                        user.setUserId(AuthCred.getInstance(context).getCurrentUserId());
//                        return user;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        });
//
//        ZAuthUser user = null;
//        try {
//            user = future.get(); // Wait for the operation to complete and get the result
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        executor.shutdown(); // Shutdown the executor service
//        return user;
//    }

}
