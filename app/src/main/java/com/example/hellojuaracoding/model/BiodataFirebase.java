package com.example.hellojuaracoding.model;


import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;


@IgnoreExtraProperties
public class BiodataFirebase {
    public     String nama;
    public     String jk;
    public     String pekerjaan;
    public     String tgl_lahir;
    public    String alamat;
    public   String email;
    public   String catatan;
    public     String tlp;

    public BiodataFirebase(String nama, String jk, String pekerjaan, String tgl_lahir, String alamat, String email, String catatan, String tlp) {
        this.nama = nama;
        this.jk = jk;
        this.pekerjaan = pekerjaan;
        this.tgl_lahir = tgl_lahir;
        this.alamat = alamat;
        this.email = email;
        this.catatan = catatan;
        this.tlp = tlp;
    }



    public  BiodataFirebase(){

    }

    @Exclude
    public HashMap<String,Object>toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("nama", nama);
        result.put("jk", jk);
        result.put("pekerjaan", pekerjaan);
        result.put("tgl_lahir", tgl_lahir);
        result.put("alamat", alamat);
        result.put("email", email);
        result.put("catatan", catatan);
        result.put("tlp", tlp);

        return  result;

    }



}

