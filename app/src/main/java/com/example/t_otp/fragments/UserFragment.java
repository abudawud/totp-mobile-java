package com.example.t_otp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.t_otp.LoginActivity;
import com.example.t_otp.MainActivity;
import com.example.t_otp.R;
import com.example.t_otp.helpers.ErrorAPI;
import com.example.t_otp.helpers.ToaLog;
import com.example.t_otp.interfaces.InterfaceAPI;
import com.example.t_otp.models.ResponseStatus;
import com.example.t_otp.utils.APIClient;
import com.example.t_otp.utils.AppPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private InterfaceAPI mApi;
    private Context ctx;
    private AppPreferences mPref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        TextView tvName, tvNIP;
        Button btnLogout, btnFLogout;

        ctx = getContext();
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        mPref = new AppPreferences(ctx);
        mApi = APIClient.getClient().create(InterfaceAPI.class);
        mPref = new AppPreferences(ctx);

        tvName = root.findViewById(R.id.user_name);
        tvNIP = root.findViewById(R.id.tv_nip);
        btnLogout = root.findViewById(R.id.btn_logout);
        btnFLogout = root.findViewById(R.id.btn_f_logout);

        tvName.setText(mPref.getFullName());
        tvNIP.setText(mPref.getNIP());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setMessage("Apakah anda yakin ingin logout ?")
                        .setTitle("Logout")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("Batal", null)
                        .create().show();
            }
        });

        btnFLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setMessage("Apakah anda yakin ingin melakukan Force-Logout ?")
                        .setTitle("Force-Logout")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clearPref();
                            }
                        })
                        .setNegativeButton("Batal", null)
                        .create().show();
            }
        });

        return root;
    }

    private void logout(){
        Call <ResponseStatus> call = mApi.logout(mPref.getNIP(), mPref.getToken());
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if(response.isSuccessful()){
                    ToaLog.info(ctx, response.body().getMessage());
                    clearPref();
                }else{
                    ToaLog.info(ctx, ErrorAPI.getMessage(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                ToaLog.err(ctx, t.getLocalizedMessage());
            }
        });
    }

    private void clearPref(){
        mPref.destroyPref();
        Intent intent = new Intent(ctx, LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}