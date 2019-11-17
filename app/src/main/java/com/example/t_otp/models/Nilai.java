package com.example.t_otp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.FormatFlagsConversionMismatchException;

public class Nilai {
    @Expose
    @SerializedName("id_nilai")
    private String idNilai;

    @Expose
    @SerializedName("tugas1")
    private Float tugas1;

    @Expose
    @SerializedName("tugas2")
    private Float tugas2;

    @Expose
    @SerializedName("uts")
    private Float uts;

    @Expose
    @SerializedName("uas")
    private Float uas;

    @Expose
    @SerializedName("praktikum")
    private Float praktikum;

    @Expose
    @SerializedName("nim")
    private String nim;

    @Expose
    @SerializedName("kode_kelas")
    private String kodeKelas;

    @SerializedName("tahun_akademik")
    private String thAkademik;

    @SerializedName("nilai_total")
    private Float nilaiTotal;

    @SerializedName("nilai_huruf")
    private String nilaiHuruf;

    @SerializedName("nip")
    private String nip;

    public void setIdNilai(String idNilai) {
        this.idNilai = idNilai;
    }

    public void setTugas1(Float tugas1) {
        this.tugas1 = tugas1;
    }

    public void setTugas2(Float tugas2) {
        this.tugas2 = tugas2;
    }

    public void setUts(Float uts) {
        this.uts = uts;
    }

    public void setUas(Float uas) {
        this.uas = uas;
    }

    public void setPraktikum(Float praktikum) {
        this.praktikum = praktikum;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setKodeKelas(String kodeKelas) {
        this.kodeKelas = kodeKelas;
    }

    public String getIdNilai() {
        return idNilai;
    }

    public Float getTugas1() {
        return tugas1;
    }

    public Float getTugas2() {
        return tugas2;
    }

    public Float getUts() {
        return uts;
    }

    public Float getUas() {
        return uas;
    }

    public Float getPraktikum() {
        return praktikum;
    }

    public String getNim() {
        return nim;
    }

    public String getKodeKelas() {
        return kodeKelas;
    }

    public String getThAkademik() {
        return thAkademik;
    }

    public Float getNilaiTotal() {
        return nilaiTotal;
    }

    public String getNilaiHuruf() {
        return nilaiHuruf;
    }

    public String getNip() {
        return nip;
    }
}
