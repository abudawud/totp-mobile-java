package com.example.t_otp.models;

import com.google.gson.annotations.SerializedName;

public class Error {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
