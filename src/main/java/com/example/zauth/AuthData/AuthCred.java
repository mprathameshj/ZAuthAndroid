package com.example.zauth.AuthData;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NavUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class AuthCred {
    private static final String PREFS_NAME = "AuthCredentials";
    private static final String USER_ID_KEY = "userId";
    private static final String AUTH_TOKEN_KEY = "authToken";
    private static final String TAG = "AuthCred";

    private static AuthCred instance;
    private SharedPreferences preferences;
    private Context context;

    private AuthCred(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.context = context;
    }

    public synchronized static AuthCred getInstance(Context context) {
        if (instance == null) {
            instance = new AuthCred(context.getApplicationContext());
        }
        return instance;
    }

    public void saveCredentials(String userId, String authToken) {
        try {
            SharedPreferences.Editor editor = preferences.edit();
            byte[] encryptedUserId = encrypt(userId.getBytes());
            byte[] encryptedAuthToken = encrypt(authToken.getBytes());
            editor.putString(USER_ID_KEY, Base64.encodeToString(encryptedUserId, Base64.DEFAULT));
            editor.putString(AUTH_TOKEN_KEY, Base64.encodeToString(encryptedAuthToken, Base64.DEFAULT));
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, "Save credentials error: " + e.toString());
            Toast.makeText(context, "Save cred "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public UserInfo getCredentials() {
        try {
            String s=preferences.getString(USER_ID_KEY,null);
            if(s==null) return null;
            byte[] encryptedUserId = Base64.decode(preferences.getString(USER_ID_KEY, null), Base64.DEFAULT);
            byte[] encryptedAuthToken = Base64.decode(preferences.getString(AUTH_TOKEN_KEY, null), Base64.DEFAULT);
            String userId = new String(decrypt(encryptedUserId));
            String authToken = new String(decrypt(encryptedAuthToken));
            UserInfo info = new UserInfo();
            info.setUserId(userId);
            info.setAuthToken(authToken);
            return info;
        } catch (Exception e) {
            Log.e(TAG, "Get credentials error: " + e.toString());
            Toast.makeText(context, "get cred " +e.toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public String getCurrentUserId() {
        try {
            String s=preferences.getString(USER_ID_KEY,null);
            if(s==null) return null;
            byte[] encryptedUserId = Base64.decode(preferences.getString(USER_ID_KEY, null), Base64.DEFAULT);

            return new String(decrypt(encryptedUserId));
        } catch (Exception e) {
            Log.e(TAG, "Get current user id error: " + e.toString());
            return null;
        }
    }

    public void deleteCredentials() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(USER_ID_KEY);
        editor.remove(AUTH_TOKEN_KEY);
        editor.apply();
    }

    private byte[] encrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
        byte[] iv = cipher.getIV();
        byte[] encryptedBytes = cipher.doFinal(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(iv);
        outputStream.write(encryptedBytes);
        return outputStream.toByteArray();
    }

    private byte[] decrypt(byte[] input) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
        byte[] iv = new byte[12]; // 12 bytes IV
        inputStream.read(iv);
        byte[] encryptedBytes = new byte[input.length - 12];
        inputStream.read(encryptedBytes);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), new GCMParameterSpec(128, iv));
        return cipher.doFinal(encryptedBytes);
    }

    private SecretKey getSecretKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        if (!keyStore.containsAlias("MyAlias")) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder("MyAlias", KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setRandomizedEncryptionRequired(false);
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        }
        return (SecretKey) keyStore.getKey("MyAlias", null);
    }
}
