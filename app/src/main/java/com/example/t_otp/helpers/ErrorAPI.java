package com.example.t_otp.helpers;

import com.example.t_otp.models.Error;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Response;

public class ErrorAPI {
    public static String getMessage(Response response){
        try {
            String error = response.errorBody().string();
            Gson gson = new GsonBuilder().create();
            Error apiErr = gson.fromJson(error, Error.class);
            return  apiErr.getMessage();
        }catch (IOException e){
            e.printStackTrace();
        }

        return "Failed parsing error body!";
    }
}
