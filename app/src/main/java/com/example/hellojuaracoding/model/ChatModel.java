package com.example.hellojuaracoding.model;

public class ChatModel {
    private  String nama;
    private  String pesan;
    private String tanggal;



    public ChatModel(String nama, String pesan,String tanggal ){
        this.nama = nama;
        this.pesan = pesan;
        this.tanggal = tanggal;


    }



    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


    private  String key;

    public  ChatModel(){

    }


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

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }




    @Override
    public String toString() {
        return "ChatModel{" +
                "nama='" + nama + '\'' +
                ", pesan='" + pesan + '\'' +
                ", tanggal='" + tanggal + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
