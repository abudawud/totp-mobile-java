package com.example.t_otp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.t_otp.helpers.CEncryption;
import com.example.t_otp.helpers.ErrorAPI;
import com.example.t_otp.helpers.ToaLog;
import com.example.t_otp.interfaces.InterfaceAPI;
import com.example.t_otp.models.AuthUser;
import com.example.t_otp.utils.APIClient;
import com.example.t_otp.utils.AppPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private InterfaceAPI mApi;

    private EditText edUsername, edPassword;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx = this;
        mApi = APIClient.getClient().create(InterfaceAPI.class);

        Button btnLogin = findViewById(R.id.btn_login);
        edUsername = findViewById(R.id.nip);
        edPassword = findViewById(R.id.password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        AuthUser auth = new AuthUser();
        auth.setPassword(edPassword.getText().toString());
        auth.setNIP(edUsername.getText().toString());

        Call<AuthUser> call = mApi.login(auth);

        call.enqueue(new Callback<AuthUser>() {
            @Override
            public void onResponse(Call<AuthUser> call, Response<AuthUser> response) {
                if(response.isSuccessful()) {
                    AuthUser user = response.body();
                    AppPreferences mPref = new AppPreferences(ctx);
                    mPref.setFullName(user.getFullName());
                    mPref.setNIP(user.getNIP());
                    mPref.setLogedIn(true);
                    mPref.setOTPKey(user.getOTPKey());
                    mPref.setToken(user.getToken());
                    mPref.setTokenValid(true);

                    MainActivity.OTP_PRIVATE_KEY = user.getOTPKey();
                    MainActivity.OTP_TOKEN_KEY = user.getToken();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    ToaLog.info(ctx, ErrorAPI.getMessage(response));
                }
            }

            @Override
            public void onFailure(Call<AuthUser> call, Throwable t) {
                ToaLog.err(ctx, t.getLocalizedMessage());
            }
        });
    }
}
