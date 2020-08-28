package com.example.hellojuaracoding.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;


@Entity(tableName = "Biodata")
public class Biodata {

    @ColumnInfo(name = "nama")
    private    String nama;
    @ColumnInfo (name = "jk")
    private    String jk;
    @ColumnInfo (name = "pekerjaan")
    private    String pekerjaan;

    @ColumnInfo (name = " tgl_lahir")
    private    String tgl_lahir;
    @ColumnInfo (name = "alamat")
    private   String alamat;
    @ColumnInfo (name = "email")
    private  String email;
    @ColumnInfo (name = "catatan")
    private  String catatan;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tlp")
    private    String tlp;
    private  String key;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @NonNull
    public String getTlp() {
        return tlp;
    }

    public void setTlp(String tlp) {
        this.tlp = tlp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }



}
