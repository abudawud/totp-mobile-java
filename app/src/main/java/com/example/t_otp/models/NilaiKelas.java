package com.example.t_otp.models;

import com.google.gson.annotations.SerializedName;

public class NilaiKelas {
    @SerializedName("kode_kelas")
    private String kdKelas;

    @SerializedName("kode_matkul")
    private String kdMatkul;

    @SerializedName("nama_matkul")
    private String namaMatkul;

    @SerializedName("nama_dosen")
    private String namaDosen;

    @SerializedName("nip")
    private String nip;


    public String getKdKelas() {
        return kdKelas;
    }

    public String getKdMatkul() {
        return kdMatkul;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }

    public String getNamaDosen() {
        return namaDosen;
    }

    public String getNip() {
        return nip;
    }
}
