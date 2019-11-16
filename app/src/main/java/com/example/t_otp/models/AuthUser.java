package com.example.t_otp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthUser {
    @Expose
    @SerializedName("nip")
    private String NIP;

    @Expose
    @SerializedName("pwd")
    private String password;

    @SerializedName("nama_dosen")
    private String FullName;

    @SerializedName("enckey")
    private String OTPKey;

    @SerializedName("token")
    private String Token;


    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getOTPKey() {
        return OTPKey;
    }

    public void setOTPKey(String OTPKey) {
        this.OTPKey = OTPKey;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
