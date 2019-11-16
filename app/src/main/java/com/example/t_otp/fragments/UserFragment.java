package com.example.t_otp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.t_otp.R;
import com.example.t_otp.utils.AppPreferences;

public class UserFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Context ctx;
        TextView tvName, tvNIP;

        ctx = getContext();
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        AppPreferences mPref = new AppPreferences(ctx);

        tvName = root.findViewById(R.id.user_name);
        tvNIP = root.findViewById(R.id.tv_nip);

        tvName.setText(mPref.getFullName());
        tvNIP.setText(mPref.getNIP());

        return root;
    }
}