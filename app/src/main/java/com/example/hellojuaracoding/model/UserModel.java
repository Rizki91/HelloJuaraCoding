package com.example.hellojuaracoding.model;

public class UserModel {
    String uid;
    String nama;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

    @Override
    public String toString() {
        return "UserModel{" +
                "uid='" + uid + '\'' +
                ", nama='" + nama + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
