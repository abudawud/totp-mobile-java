package com.example.t_otp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_otp.R;
import com.example.t_otp.adapters.JadwalAdapter;
import com.example.t_otp.helpers.ErrorAPI;
import com.example.t_otp.helpers.ToaLog;
import com.example.t_otp.interfaces.InterfaceAPI;
import com.example.t_otp.models.Jadwal;
import com.example.t_otp.utils.APIClient;
import com.example.t_otp.utils.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalFragment extends Fragment {
    private Context ctx;
    private RecyclerView recyclerView;
    private JadwalAdapter adapter;
    private InterfaceAPI mAPI;
    private ArrayList<Jadwal> jadwalArrayList = new ArrayList<>();
    private AppPreferences mPref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_jadwal, container, false);
        ctx = getContext();

        mAPI = APIClient.getClient().create(InterfaceAPI.class);
        mPref = new AppPreferences(ctx);

        recyclerView = root.findViewById(R.id.recycler_view);
        adapter = new JadwalAdapter(jadwalArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        recyclerView.setAdapter(adapter);

        fetchJadwal();

        return root;
    }

    private void fetchJadwal(){
        Call<List<Jadwal>> call = mAPI.fetchJadwal(mPref.getNIP(), mPref.getToken());
        call.enqueue(new Callback<List<Jadwal>>() {
            @Override
            public void onResponse(Call<List<Jadwal>> call, Response<List<Jadwal>> response) {
                if(response.isSuccessful()){
                    if(response.body().size() == 0){
                        ToaLog.info(ctx, "Tidak Ada Jadwal!");
                    }else{
                        jadwalArrayList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    ToaLog.info(ctx, ErrorAPI.getMessage(response));
                }
            }

            @Override
            public void onFailure(Call<List<Jadwal>> call, Throwable t) {
                ToaLog.err(ctx, t.getLocalizedMessage());
            }
        });
    }
}