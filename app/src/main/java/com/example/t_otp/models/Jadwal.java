package com.example.t_otp.models;

import com.google.gson.annotations.SerializedName;

public class Jadwal {
    @SerializedName("kode_kelas")
    private String kodeKelas;

    @SerializedName("kode_matkul")
    private String kodeMatkul;

    @SerializedName("nama_matkul")
    private String namaMatkul;

    @SerializedName("kode_ruangan")
    private String kodeRuangan;

    @SerializedName("nama_ruangan")
    private String namaRuangan;

    @SerializedName("hari")
    private String hari;

    @SerializedName("jam")
    private String jam;

    public String getKodeKelas() {
        return kodeKelas;
    }

    public String getKodeMatkul() {
        return kodeMatkul;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }

    public String getKodeRuangan() {
        return kodeRuangan;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    public String getHari() {
        return hari;
    }

    public String getJam() {
        return jam;
    }
}
