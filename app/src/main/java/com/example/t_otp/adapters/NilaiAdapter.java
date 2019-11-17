package com.example.t_otp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_otp.R;
import com.example.t_otp.models.Nilai;

import java.util.ArrayList;

public abstract class NilaiAdapter extends RecyclerView.Adapter<NilaiAdapter.NilaiViewHolder> {


    private ArrayList<Nilai> dataList;


    public NilaiAdapter(ArrayList<Nilai> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public NilaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview_nilai, parent, false);
        return new NilaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NilaiViewHolder holder, final int position) {
        Nilai nilai = dataList.get(position);

        holder.tvIdNilai.setText(nilai.getIdNilai());
        holder.tvTahun.setText("(" + nilai.getThAkademik() + ")");
        holder.tvNIM.setText(nilai.getNim());
        holder.tvKdKelas.setText(nilai.getKodeKelas());
        holder.tvTugas1.setText(nilai.getTugas1().toString());
        holder.tvTugas2.setText(nilai.getTugas2().toString());
        holder.tvPraktikum.setText(nilai.getPraktikum().toString());
        holder.tvUTS.setText(nilai.getUts().toString());
        holder.tvUas.setText(nilai.getUas().toString());
        holder.tvNilaiTotal.setText(nilai.getNilaiTotal().toString());
        holder.tvNilaiHuruf.setText("Nilai Huruf : " + nilai.getNilaiHuruf());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class NilaiViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIdNilai, tvTahun, tvNIM, tvKdKelas,
                tvTugas1, tvTugas2, tvUas, tvUTS,
                tvPraktikum, tvNilaiTotal, tvNilaiHuruf;

        private NilaiViewHolder(View itemView) {
            super(itemView);
            tvKdKelas = itemView.findViewById(R.id.tv_kode_kelas);
            tvIdNilai = itemView.findViewById(R.id.tv_id_nilai);
            tvTahun = itemView.findViewById(R.id.tv_th_akademik);
            tvNIM = itemView.findViewById(R.id.tv_nim);
            tvTugas1 = itemView.findViewById(R.id.tv_tugas_1);
            tvTugas2 = itemView.findViewById(R.id.tv_tugas_2);
            tvUas = itemView.findViewById(R.id.tv_uas);
            tvUTS = itemView.findViewById(R.id.tv_uts);
            tvPraktikum = itemView.findViewById(R.id.tv_praktikum);
            tvNilaiTotal = itemView.findViewById(R.id.tv_nilai_total);
            tvNilaiHuruf = itemView.findViewById(R.id.tv_nilai_huruf);
        }
    }

    protected abstract void onItemClick(int position);
}
