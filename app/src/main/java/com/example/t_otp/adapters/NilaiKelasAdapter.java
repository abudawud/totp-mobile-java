package com.example.t_otp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_otp.R;
import com.example.t_otp.models.Nilai;
import com.example.t_otp.models.NilaiKelas;

import java.util.ArrayList;

public abstract class NilaiKelasAdapter extends RecyclerView.Adapter<NilaiKelasAdapter.NilaiKelasViewHolder> {


    private ArrayList<NilaiKelas> dataList;


    public NilaiKelasAdapter(ArrayList<NilaiKelas> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public NilaiKelasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview_nilai_kelas, parent, false);
        return new NilaiKelasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NilaiKelasViewHolder holder, final int position) {
        NilaiKelas nilaiKelas = dataList.get(position);

        holder.tvKdKelas.setText(nilaiKelas.getKdKelas());
        holder.tvKdMatkul.setText(nilaiKelas.getKdMatkul());
        holder.tvNamaMatkul.setText(nilaiKelas.getNamaMatkul());

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

    public class NilaiKelasViewHolder extends RecyclerView.ViewHolder{
        private TextView tvKdKelas, tvKdMatkul, tvNamaMatkul;

        private NilaiKelasViewHolder(View itemView) {
            super(itemView);
            tvKdKelas = itemView.findViewById(R.id.tv_kode_kelas);
            tvKdMatkul = itemView.findViewById(R.id.tv_kode_matkul);
            tvNamaMatkul = itemView.findViewById(R.id.tv_nama_matkul);
        }
    }

    protected abstract void onItemClick(int position);
}
