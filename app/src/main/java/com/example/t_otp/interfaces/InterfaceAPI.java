package com.example.t_otp.interfaces;

import com.example.t_otp.models.AuthUser;
import com.example.t_otp.models.Jadwal;
import com.example.t_otp.models.Nilai;
import com.example.t_otp.models.ResponseStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterfaceAPI {
    @POST("login")
    Call<AuthUser> login(@Body AuthUser auth);

    @POST("login/logout")
    Call<ResponseStatus> logout(
            @Header("X-Auth-Nip") String authNIP,
            @Header("X-Auth-Token") String authToken,
            @Body AuthUser auth
    );

    @GET("jadwal")
    Call<List<Jadwal>> fetchJadwal(
            @Header("X-Auth-Nip") String authNIP,
            @Header("X-Auth-Token") String authToken
    );

    @GET("nilai")
    Call<List<Nilai>> fetchNilai(
            @Header("X-Auth-Nip") String authNIP,
            @Header("X-Auth-Token") String authToken
    );

    @DELETE("nilai/{id}")
    Call<ResponseStatus> deleteNilai(
            @Path("id") String id,
            @Header("X-Auth-Nip") String authNIP,
            @Header("X-Auth-Token") String authToken
    );

    @POST("nilai")
    Call<ResponseStatus> addNilai(
            @Header("X-Auth-Nip") String authNIP,
            @Header("X-Auth-Token") String authToken,
            @Body Nilai nilai
    );

    @PATCH("nilai/{id}")
    Call<ResponseStatus> patchNilai(
            @Path("id") String id,
            @Header("X-Auth-Nip") String authNIP,
            @Header("X-Auth-Token") String authToken,
            @Body Nilai nilai
    );

    @GET("nilai/{id}")
    Call<Nilai> getNilai(
            @Path("id") String id,
            @Header("X-Auth-Nip") String authNIP,
            @Header("X-Auth-Token") String authToken
    );
}
