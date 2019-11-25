package com.example.t_otp.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.t_otp.MainActivity;
import com.example.t_otp.NilaiActivity;
import com.example.t_otp.R;
import com.example.t_otp.adapters.NilaiKelasAdapter;
import com.example.t_otp.helpers.ErrorAPI;
import com.example.t_otp.helpers.ToaLog;
import com.example.t_otp.interfaces.InterfaceAPI;
import com.example.t_otp.models.NilaiKelas;
import com.example.t_otp.utils.APIClient;
import com.example.t_otp.utils.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NilaiKelasFragment extends Fragment {
    private Context ctx;
    private NilaiKelasAdapter adapter;
    private InterfaceAPI mAPI;
    private ArrayList<NilaiKelas> nilaiKelasArrayList = new ArrayList<>();
    private AppPreferences mPref;

    public NilaiKelasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_nilai_kelas, container, false);

        RecyclerView recyclerView;

        ctx = getContext();

        mAPI = APIClient.getClient().create(InterfaceAPI.class);
        mPref = new AppPreferences(ctx);

        recyclerView = root.findViewById(R.id.recycler_view);
        adapter = new NilaiKelasAdapter(nilaiKelasArrayList) {
            @Override
            protected void onItemClick(int position) {
                NilaiKelas nilaiKelas = nilaiKelasArrayList.get(position);
                Intent intent = new Intent(ctx, NilaiActivity.class);
                intent.putExtra(MainActivity.EXTRA_KD_KELAS_KEY, nilaiKelas.getKdKelas());
                startActivity(intent);
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        recyclerView.setAdapter(adapter);

        fetchNilaiKelas();

        return root;
    }

    private void fetchNilaiKelas(){
        Call<List<NilaiKelas>> call = mAPI.getNilaiKelas(mPref.getNIP(), mPref.getToken());

        call.enqueue(new Callback<List<NilaiKelas>>() {
            @Override
            public void onResponse(Call<List<NilaiKelas>> call, Response<List<NilaiKelas>> response) {
                if(response.isSuccessful()){
                    if(response.body().size() == 0){
                        ToaLog.info(ctx, "Tidak Ada Nilai!");
                    }else{
                        nilaiKelasArrayList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                    }
                }else if(response.code() == 401){
                    mPref.setTokenValid(false);
                    ToaLog.info(ctx, ErrorAPI.getMessage(response));
                }else{
                    ToaLog.info(ctx, ErrorAPI.getMessage(response));
                }
            }

            @Override
            public void onFailure(Call<List<NilaiKelas>> call, Throwable t) {
                ToaLog.err(ctx, t.getLocalizedMessage());
            }
        });
    }

}
