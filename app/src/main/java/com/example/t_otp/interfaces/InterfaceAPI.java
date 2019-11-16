package com.example.t_otp.interfaces;

import com.example.t_otp.models.AuthUser;
import com.example.t_otp.models.Jadwal;
import com.example.t_otp.models.ResponseStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InterfaceAPI {
    @POST("login")
    Call<AuthUser> login(@Body String auth);

    @POST("login/logout")
    Call<ResponseStatus> logout(
            @Header("X-Auth-Nip") String authNIP,
            @Header("X-Auth-Token") String authToken
    );

    @GET("jadwal")
    Call<List<Jadwal>> fetchJadwal(
            @Header("X-Auth-Nip") String authNIP,
            @Header("X-Auth-Token") String authToken
    );
}
