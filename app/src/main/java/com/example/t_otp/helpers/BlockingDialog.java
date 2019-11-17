package com.example.t_otp.helpers;

import android.app.ProgressDialog;
import android.content.Context;

public class BlockingDialog{
    private static ProgressDialog dialog = null;

    public static ProgressDialog show(Context ctx, String msg){
        if(dialog == null){
            dialog = new ProgressDialog(ctx);
            dialog.setCancelable(false);
        }

        dialog.setMessage(msg);
        dialog.show();

        return dialog;
    }


}
