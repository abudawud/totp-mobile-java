package com.example.t_otp.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_otp.MainActivity;
import com.example.t_otp.R;
import com.example.t_otp.adapters.NilaiAdapter;
import com.example.t_otp.helpers.ErrorAPI;
import com.example.t_otp.helpers.BlockingDialog;
import com.example.t_otp.helpers.ToaLog;
import com.example.t_otp.interfaces.InterfaceAPI;
import com.example.t_otp.models.Nilai;
import com.example.t_otp.models.ResponseStatus;
import com.example.t_otp.utils.APIClient;
import com.example.t_otp.utils.AppPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NilaiFragment extends Fragment {
    private Context ctx;
    private NilaiAdapter adapter;
    private InterfaceAPI mAPI;
    private ArrayList<Nilai> nilaiArrayList = new ArrayList<>();
    private AppPreferences mPref;

    private String mKdKelas;

    private final Integer PROCESS_ADD_NILAI = 1;
    private final Integer PROCESS_EDIT_NILAI = 2;

    private static final String TAG = "NilaiFragment";

    public static Fragment newInstance(String kdKelas){
        NilaiFragment fragment = new NilaiFragment();

        Bundle args = new Bundle();
        args.putString(MainActivity.EXTRA_KD_KELAS_KEY, kdKelas);
        fragment.setArguments(args);

        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_nilai, container, false);
        RecyclerView recyclerView;
        FloatingActionButton fab;

        ctx = getContext();

        mAPI = APIClient.getClient().create(InterfaceAPI.class);
        mPref = new AppPreferences(ctx);

        recyclerView = root.findViewById(R.id.recycler_view);
        fab = root.findViewById(R.id.fab_add);
        adapter = new NilaiAdapter(nilaiArrayList){
            @Override
            protected void onItemClick(final int position) {
                String [] options = {"Edit", "Delete"};

                new AlertDialog.Builder(ctx).setTitle("Option")
                        .setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        editNilai(position);
                                        break;

                                    case 1:
                                        new AlertDialog.Builder(ctx)
                                                .setTitle("Delete Nilai")
                                                .setMessage("Yakin akan menghapus nilai ini ?")
                                                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        deleteNilai(position);
                                                    }
                                                })
                                                .setNegativeButton("Batal", null)
                                                .create().show();
                                        break;
                                }
                            }
                        })
                        .create().show();
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNilai();
            }
        });

        Bundle args = getArguments();
        String kdKelas = "NONE";

        if(args != null){
            kdKelas = args.getString(MainActivity.EXTRA_KD_KELAS_KEY);
        }

        mKdKelas = kdKelas;
        fetchNilai(kdKelas);

        return root;
    }

    private void deleteNilai(final int pos){
        Nilai nilai = nilaiArrayList.get(pos);

        final ProgressDialog dialog = BlockingDialog.show(ctx, "Loading...");
        Call<ResponseStatus> call = mAPI.deleteNilai(nilai.getIdNilai(), mPref.getNIP(), mPref.getToken());
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                dialog.dismiss();
                if(response.isSuccessful()){
                    nilaiArrayList.remove(pos);
                    adapter.notifyItemRemoved(pos);
                    ToaLog.info(ctx, response.body().getMessage());
                }else if(response.code() == 401){
                    mPref.setTokenValid(false);
                    ToaLog.info(ctx, ErrorAPI.getMessage(response));
                }else{
                    ToaLog.info(ctx, ErrorAPI.getMessage(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {

            }
        });
    }

    private void editNilai(final int pos){
        Log.d(TAG, "editNilai: " + mPref.getToken());
        Nilai nilai = nilaiArrayList.get(pos);

        EditText edIDNilai, edNIM, edKdKelas,
                edTugas1, edTugas2, edPraktikum,
                edUTS, edUAS;

        LayoutInflater inflater = LayoutInflater.from(ctx);
        final View view = inflater.inflate(R.layout.dialog_add_nilai, null);

        ((TextView)view.findViewById(R.id.title_dialog))
                .setText(ctx.getResources().getString(R.string.lbl_form_edit_nilai));

        ((ImageView)view.findViewById(R.id.icn_dialog))
                .setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_edit_black_24dp));

        edIDNilai = view.findViewById(R.id.ed_id_nilai);
        edNIM = view.findViewById(R.id.ed_nim);
        edKdKelas = view.findViewById(R.id.ed_kd_kelas);
        edTugas1 = view.findViewById(R.id.ed_tugas_1);
        edTugas2 = view.findViewById(R.id.ed_tugas_2);
        edPraktikum = view.findViewById(R.id.ed_praktikum);
        edUTS = view.findViewById(R.id.ed_uts);
        edUAS = view.findViewById(R.id.ed_uas);

        edIDNilai.setEnabled(false);

        edIDNilai.setText(nilai.getIdNilai());
        edNIM.setText(nilai.getNim());
        edKdKelas.setText(nilai.getKodeKelas());
        edTugas1.setText(nilai.getTugas1().toString());
        edTugas2.setText(nilai.getTugas2().toString());
        edPraktikum.setText(nilai.getPraktikum().toString());
        edUTS.setText(nilai.getUts().toString());
        edUAS.setText(nilai.getUas().toString());

        final AlertDialog dialog = new AlertDialog.Builder(ctx).setView(view)
                .setNegativeButton("Batal", null)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // DO NOTHING
                    }
                })
                .create();

        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesNilai(view, dialog, pos, PROCESS_EDIT_NILAI);
            }
        });
    }

    private void addNilai(){
        LayoutInflater inflater = LayoutInflater.from(ctx);
        final View view = inflater.inflate(R.layout.dialog_add_nilai, null);

        EditText editText = view.findViewById(R.id.ed_kd_kelas);
        editText.setText(mKdKelas);

        final AlertDialog dialog = new AlertDialog.Builder(ctx).setView(view)
                .setNegativeButton("Batal", null)
                .setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // DO NOTHING
                    }
                })
                .create();

        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesNilai(view, dialog, 0, PROCESS_ADD_NILAI);
            }
        });

    }

    private void prosesNilai(View view, final DialogInterface dialog, final int pos, final int mode){
        EditText edIDNilai, edNIM, edKdKelas,
                edTugas1, edTugas2, edPraktikum,
                edUTS, edUAS;

        final ProgressBar progressBar;

        edIDNilai = view.findViewById(R.id.ed_id_nilai);
        edNIM = view.findViewById(R.id.ed_nim);
        edKdKelas = view.findViewById(R.id.ed_kd_kelas);
        edTugas1 = view.findViewById(R.id.ed_tugas_1);
        edTugas2 = view.findViewById(R.id.ed_tugas_2);
        edPraktikum = view.findViewById(R.id.ed_praktikum);
        edUTS = view.findViewById(R.id.ed_uts);
        edUAS = view.findViewById(R.id.ed_uas);
        progressBar = view.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);

        final Nilai nilai = new Nilai();

        nilai.setIdNilai(edIDNilai.getText().toString());
        nilai.setNim(edNIM.getText().toString());
        nilai.setKodeKelas(edKdKelas.getText().toString());
        nilai.setTugas1(Float.parseFloat(edTugas1.getText().toString()));
        nilai.setTugas2(Float.parseFloat(edTugas2.getText().toString()));
        nilai.setPraktikum(Float.parseFloat(edPraktikum.getText().toString()));
        nilai.setUts(Float.parseFloat(edUTS.getText().toString()));
        nilai.setUas(Float.parseFloat(edUAS.getText().toString()));

        Log.d(TAG, "prosesNilai: " + nilai.getIdNilai());

        Call<ResponseStatus> call;

        if(mode == PROCESS_ADD_NILAI){
             call = mAPI.addNilai(mPref.getNIP(), mPref.getToken(), nilai);
        }else{
            call = mAPI.patchNilai(nilai.getIdNilai(), mPref.getNIP(), mPref.getToken(), nilai);
        }

        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){
                    if(mode == PROCESS_EDIT_NILAI){
                        getNilai(nilai.getIdNilai(), PROCESS_EDIT_NILAI, pos);
                    }else if(mode == PROCESS_ADD_NILAI){
                        getNilai(nilai.getIdNilai(), PROCESS_ADD_NILAI, pos);
                    }

                    ToaLog.info(ctx, response.body().getMessage());
                    dialog.dismiss();
                }else if(response.code() == 401){
                    mPref.setTokenValid(false);
                    ToaLog.info(ctx, ErrorAPI.getMessage(response));
                }else{
                    ToaLog.info(ctx, ErrorAPI.getMessage(response));
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                ToaLog.err(ctx, t.getLocalizedMessage());
            }
        });
    }

    private void fetchNilai(String kdKelas){
        Call<List<Nilai>> call = mAPI.fetchNilai(kdKelas, mPref.getNIP(), mPref.getToken());
        call.enqueue(new Callback<List<Nilai>>() {
            @Override
            public void onResponse(Call<List<Nilai>> call, Response<List<Nilai>> response) {
                if(response.isSuccessful()){
                    if(response.body().size() == 0){
                        ToaLog.info(ctx, "Tidak Ada Nilai!");
                    }else{
                        nilaiArrayList.addAll(response.body());
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
            public void onFailure(Call<List<Nilai>> call, Throwable t) {
                ToaLog.err(ctx, t.getLocalizedMessage());
            }
        });
    }

    private void getNilai(String id, final int mode, final int pos){
        Call<Nilai> call = mAPI.getNilai(id, mPref.getNIP(), mPref.getToken());

        call.enqueue(new Callback<Nilai>() {
            @Override
            public void onResponse(Call<Nilai> call, Response<Nilai> response) {
                if(response.isSuccessful()){
                    if(response.body() == null){
                        ToaLog.info(ctx, "Nilai tidak ditemukan!");
                    }else{
                        if(mode == PROCESS_ADD_NILAI){
                            nilaiArrayList.add(response.body());
                            adapter.notifyItemInserted(nilaiArrayList.size() - 1);
                        }else if(mode == PROCESS_EDIT_NILAI){
                            nilaiArrayList.set(pos, response.body());
                            adapter.notifyItemChanged(pos);
                        }
                    }
                }else if(response.code() == 401){
                    mPref.setTokenValid(false);
                    ToaLog.info(ctx, ErrorAPI.getMessage(response));
                }else{
                    ToaLog.info(ctx, ErrorAPI.getMessage(response));
                }
            }

            @Override
            public void onFailure(Call<Nilai> call, Throwable t) {
                ToaLog.err(ctx, t.getLocalizedMessage());
            }
        });
    }
}