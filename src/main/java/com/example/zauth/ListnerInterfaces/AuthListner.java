package com.example.zauth.ListnerInterfaces;

public interface AuthListner {
    public void onSuccess();
    public void onFail(Exception e,int code);
}
