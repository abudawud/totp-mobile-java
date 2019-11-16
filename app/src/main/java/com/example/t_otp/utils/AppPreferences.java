package com.example.t_otp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private final String PREF_KEY_FULL_NAME = "PREF_KEY_FULL_NAME";
    private final String PREF_KEY_TOKEN = "PREF_KEY_TOKEN";
    private final String PREF_KEY_OTP_KEY = "PREF_KEY_OTP_KEY";
    private final String PREF_KEY_NIP = "PREF_KEY_NIP";
    private final String PREF_KEY_IS_LOGED_IN = "PREF_KEY_IS_LOGED_IN";

    private static SharedPreferences mPrefs;

    public AppPreferences(Context context) {
        if(mPrefs == null)
            mPrefs = context.getSharedPreferences("M_PREF", Context.MODE_PRIVATE);
    }

    public boolean isLogedIn(){
        return mPrefs.getBoolean(PREF_KEY_IS_LOGED_IN, false);
    }

    public void setLogedIn(Boolean status){
        mPrefs.edit().putBoolean(PREF_KEY_IS_LOGED_IN, status).apply();
    }

    public String getFullName(){
        return mPrefs.getString(PREF_KEY_FULL_NAME, null);
    }

    public void setFullName(String str){
        mPrefs.edit().putString(PREF_KEY_FULL_NAME, str).apply();
    }

    public String getToken(){
        return mPrefs.getString(PREF_KEY_TOKEN, null);
    }

    public void setToken(String str){
        mPrefs.edit().putString(PREF_KEY_TOKEN, str).apply();
    }

    public String getOTPKey(){
        return mPrefs.getString(PREF_KEY_OTP_KEY, null);
    }

    public void setOTPKey(String str){
        mPrefs.edit().putString(PREF_KEY_OTP_KEY, str).apply();
    }

    public String getNIP(){
        return mPrefs.getString(PREF_KEY_NIP, null);
    }

    public void setNIP(String str){
        mPrefs.edit().putString(PREF_KEY_NIP, str).apply();
    }
}
