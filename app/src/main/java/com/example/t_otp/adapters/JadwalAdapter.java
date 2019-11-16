package com.example.t_otp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t_otp.R;
import com.example.t_otp.models.Jadwal;

import java.util.ArrayList;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.JadwalViewHolder> {


    private ArrayList<Jadwal> dataList;

    public JadwalAdapter(ArrayList<Jadwal> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public JadwalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview_jadwal, parent, false);
        return new JadwalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JadwalViewHolder holder, int position) {
        Jadwal jadwal = dataList.get(position);

        holder.tvNmMatkul.setText(jadwal.getNamaMatkul());
        holder.tvKdKelas.setText("(" + jadwal.getKodeKelas() + ")");
        holder.tvDateTime.setText(jadwal.getHari() + ", " + jadwal.getJam());
        holder.tvNmRuangan.setText(jadwal.getNamaRuangan());
        holder.tvKdRuangan.setText("(" + jadwal.getKodeRuangan() + ")");
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class JadwalViewHolder extends RecyclerView.ViewHolder{
        private TextView tvKdKelas, tvNmMatkul, tvNmRuangan, tvKdRuangan, tvDateTime;

        private JadwalViewHolder(View itemView) {
            super(itemView);
            tvKdKelas = itemView.findViewById(R.id.tv_kode_kelas);
            tvNmMatkul = itemView.findViewById(R.id.tv_matkul);
            tvNmRuangan = itemView.findViewById(R.id.tv_nama_ruangan);
            tvKdRuangan = itemView.findViewById(R.id.tv_kode_ruangan);
            tvDateTime = itemView.findViewById(R.id.tv_date_time_jadwal);
        }
    }
}
