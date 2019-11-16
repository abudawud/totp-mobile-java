package com.example.t_otp.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ToaLog {
    private static final String TAG = "[ToaLog]";

    public static void info(Context ctx, String msg){
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "info: " + msg);
    }

    public static void err(Context ctx, String msg){
        Toast.makeText(ctx, "Something wrong happened!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "err: " + msg);
    }
}
