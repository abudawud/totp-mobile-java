package com.example.t_otp.interfaces;

import com.example.t_otp.models.AuthUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterfaceAPI {
    @POST("login")
    Call<AuthUser> login(@Body String auth);
}
