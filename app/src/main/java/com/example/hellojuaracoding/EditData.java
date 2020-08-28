package com.example.hellojuaracoding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hellojuaracoding.model.Biodata;
import com.example.hellojuaracoding.utility.SharedPrefUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.text.TextUtils.isEmpty;

public class EditData extends AppCompatActivity {

   private RadioButton rbPria1,rbWanita1;
   private Spinner spnPekerjaan1;
    private CalendarView calendarLahir1;
    private EditText txtNama1,txtAlamat1,txtTelepon1, txtEmail1,txtCatatan1;
    private Button btnSimpan1, btnBatal1;
    private String tanggal1="";
    private AppDatabase mDb;
    private DatabaseReference mDatabase;
    private  String cekNama,cekAlamat,cekTelepon,cekEmail,cekCatatan,cekPekerjaan,cekJk,cekTgl;
    private Biodata biodata;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        txtNama1 = findViewById(R.id.txtNamaBaru);
        rbPria1 = findViewById(R.id.rbLakiBaru);
        rbWanita1 = findViewById(R.id.rbPerempuanBaru);
        spnPekerjaan1 = findViewById(R.id.spnPekerjaanBaru);
        calendarLahir1 = findViewById(R.id.txtCalenderBaru);
        txtAlamat1 = findViewById(R.id.txtalamatBaru);
//        txtTelepon = findViewById(R.id.txtTelpon);
        txtEmail1 = findViewById(R.id.txtEmailBaru);
        txtCatatan1 = findViewById(R.id.txtCatatanBaru);

        btnBatal1 = findViewById(R.id.btnBatalBaru);
        btnSimpan1 = findViewById(R.id.btnSimpanBaru);
        mAuth = FirebaseAuth.getInstance();
        mDb = AppDatabase.getInstance(getApplicationContext());


        calendarLahir1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
                Date date = new Date(year-1900,month,dayOfMonth);
                tanggal1= sdf.format(date);
                Toast.makeText(EditData.this,tanggal1 ,Toast.LENGTH_LONG).show();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        getData();

        btnBatal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSimpan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



//                cekNama = txtNama1.getText().toString();
//                cekJk = rbPria1.getText().toString();
//                cekJk = rbWanita1.getText().toString();
//                cekPekerjaan = spnPekerjaan1.getSelectedItem().toString();
//                cekTgl = tanggal1;
//                cekAlamat = txtAlamat1.getText().toString();
////        cekTelepon = txtTelepon.getText().toString();
//                cekEmail = txtEmail1.getText().toString();
//                cekCatatan = txtCatatan1.getText().toString();
//
//                if(isEmpty(cekNama) || isEmpty(cekJk) || isEmpty(cekPekerjaan) || isEmpty(cekTgl) || isEmpty(cekAlamat) || isEmpty(cekEmail) || isEmpty(cekCatatan)){
//                    Toast.makeText(EditData.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
//                }else {


                    Biodata biodata = new Biodata();

                    biodata.setNama(txtNama1.getText().toString());

                    if (rbPria1.isChecked()) {
                        biodata.setJk("Pria");

                    } else if (rbWanita1.isChecked()) {
                        biodata.setJk("Wanita");
                    } else {
                        biodata.setJk("Tidak diketahui");
                    }

                    biodata.setPekerjaan(spnPekerjaan1.getSelectedItem().toString());
                    biodata.setTgl_lahir(tanggal1);
                    biodata.setAlamat(txtAlamat1.getText().toString());
                    biodata.setEmail(txtEmail1.getText().toString());
                    biodata.setTlp(txtTelepon1.getText().toString());
                    biodata.setCatatan(txtCatatan1.getText().toString());
                    updateBiodata(biodata);


                }

//                }

//            }
        });


    }

    private  void simpan(){
        final String nama = txtNama1.getText().toString();
              String jk = rbPria1.getText().toString();
                     jk = rbWanita1.getText().toString();
        final  String pekerjaan = spnPekerjaan1.getSelectedItem().toString();
        final  String alamat = txtAlamat1.getText().toString();
        final  String tlp = txtTelepon1.getText().toString();
        final String email = txtEmail1.getText().toString();
        final  String catatan = txtCatatan1.getText().toString();





    }

    public Biodata   getData( ){



        final String getNama = getIntent().getExtras().getString("dataNama");
        final String getJk = getIntent().getExtras().getString("dataJk");
        final String getPekerjan = getIntent().getExtras().getString("dataPekerjaan");
        final String getTgl = getIntent().getExtras().getString("dataTanggal");
        final  String getAlamat = getIntent().getExtras().getString("dataAlamat");
        final String getEmail = getIntent().getExtras().getString("dataEmail");
        final String getCatatan = getIntent().getExtras().getString("dataCatatan");


        txtNama1.setText(getNama);

        if(getJk.equalsIgnoreCase("pria")){
            rbPria1.setChecked(true);
            rbWanita1.setChecked(false);
        }else if(getJk.equalsIgnoreCase("Wanita")){
            rbPria1.setChecked(false);
            rbWanita1.setChecked(true);

        }else {

            rbPria1.setChecked(false);
            rbWanita1.setChecked(false);
        }

        List<String> lstPekerjaan = Arrays.asList(getResources().getStringArray(R.array.pekerjaan));
        for(int x = 0 ;  x < lstPekerjaan.size();x++){

            if(lstPekerjaan.get(x).equalsIgnoreCase(getPekerjan)){
                spnPekerjaan1.setSelection(x);
            }

        }

        Date dateDummy = null;
        try {
            dateDummy =new SimpleDateFormat("dd-MMMM-yyyy").parse(getTgl);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarLahir1.setDate(dateDummy.getTime());

        txtAlamat1.setText(getAlamat);
        txtEmail1.setText(getEmail);
        txtCatatan1.setText(getCatatan);


        return  biodata;
    }

    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    public boolean checkMandatory(){
        boolean pass = true;
        if(isEmpty(txtNama1.getText().toString())){
            pass = false;
            txtNama1.setError("Masukan nama, mandatory");

        }

        if(isEmpty(txtEmail1.getText().toString() )|| !Patterns.EMAIL_ADDRESS.matcher(txtEmail1.getText().toString()).matches()){
            pass = false;
            txtEmail1.setError("Masukan email dengan format yang benar");
        }

        return pass;
    }


    private  void   updateBiodata (Biodata biodata ){


        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        String userID = mAuth.getUid();
        mDatabase.child("Biodata")
                .child(userID)
                .child("Data")
                .child(getKey)
                .setValue(biodata);
    }

    @SuppressLint("StaticFieldLeak")
    private void updateData(final Biodata biodata){
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                //Menjalankan proses insert data
                return mDb.biodataDao().updateBiodata(biodata);
            }

            @Override
            protected void onPostExecute(Integer status) {
                //Menandakan bahwa data berhasil disimpan
                Toast.makeText(EditData.this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditData.this, ListBiodata.class));
                finish();
            }
        }.execute();
    }

    public void showErrorDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditData.this);
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage("Mohon isi field yang mandatory").setIcon(R.drawable.ic_close).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EditData.this,"Cancel ditekan",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }
    public void showErrorDialog2(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditData.this);
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage("Mohon masukan telepon yang berbeda").setIcon(R.drawable.ic_close).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EditData.this,"Cancel ditekan",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }


    public void showJsonDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditData.this);
        alertDialog.setTitle("Sukses");
        alertDialog.setMessage("Data Tersimpan").setIcon(R.drawable.ic_about).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();


            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EditData.this,"Cancel ditekan",Toast.LENGTH_LONG).show();

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

}