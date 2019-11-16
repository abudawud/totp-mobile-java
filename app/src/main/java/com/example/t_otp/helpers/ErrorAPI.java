package com.example.t_otp.helpers;

import com.example.t_otp.models.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class ErrorAPI {
    public static String getMessage(retrofit2.Response response){
        try {
            String error = response.errorBody().string();
            Gson gson = new GsonBuilder().create();
            ResponseStatus apiErr = gson.fromJson(error, ResponseStatus.class);
            return  apiErr.getMessage();
        }catch (IOException e){
            e.printStackTrace();
        }

        return "Failed parsing error body!";
    }
}
