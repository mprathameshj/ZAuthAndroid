package com.example.zauth.ListnerInterfaces;

public interface OtpListner {
    public void onOtpSent();
    public void onFail(Exception e);
}
