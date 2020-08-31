package com.example.hellojuaracoding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.text.TextUtils.isEmpty;

public class EditData extends AppCompatActivity {

    private RadioButton rbPria1, rbWanita1;
    private Spinner spnPekerjaan1;
    private CalendarView calendarLahir1;
    private TextView tvDateReselut1;
    private EditText txtNama1, txtAlamat1, txtTelepon1, txtEmail1, txtCatatan1;
    private Button btnSimpan1, btnBatal1,btnTgl1;
    private String tanggal1 = "";
    private AppDatabase mDb;
    private DatabaseReference mDatabase;
    private Biodata biodata;
    private FirebaseAuth mAuth;
    private  int data;


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
        txtTelepon1 = findViewById(R.id.txtTelponBaru);
        txtEmail1 = findViewById(R.id.txtEmailBaru);
        txtCatatan1 = findViewById(R.id.txtCatatanBaru);


        btnBatal1 = findViewById(R.id.btnBatalBaru);
        btnSimpan1 = findViewById(R.id.btnSimpanBaru);

        mDb = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "biodata_db").build();


        calendarLahir1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
                Date date = new Date(year - 1900, month, dayOfMonth);
                tanggal1 = sdf.format(date);
                Toast.makeText(EditData.this, tanggal1, Toast.LENGTH_LONG).show();
            }
        });


        btnBatal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        data = getIntent().getIntExtra("data", 0);
        getData();

//        mappingData();
        btnSimpan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkMandatory()){

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Biodata biodata = null ;

                            if (biodata != null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showErrorDialog2();

                                    }
                                });
                            }else {
                                if(data != 10){
                                    mDb.biodataDao().updateBiodata(generateObjectData());
                                    String getUserID = mAuth.getCurrentUser().getUid();
                                    mDatabase.child("Biodata").child(getUserID).child("Data").child(generateObjectData().getTlp()).setValue(generateObjectData());


                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showJsonDialog();
                                    }
                                });
                            }

                        }
                    }).start();




                }else{
                    showErrorDialog();
                }




            }
        });


    }

    public Biodata generateObjectData() {

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
        biodata.setTgl_lahir(tanggal1);
        biodata.setAlamat(txtAlamat1.getText().toString());
        biodata.setEmail(txtEmail1.getText().toString());
        biodata.setTlp(txtTelepon1.getText().toString());
        biodata.setCatatan(txtCatatan1.getText().toString());


        return biodata;

    }


    public void getData() {

//        biodata = (Biodata)getIntent().getSerializableExtra("data");

        final Bundle bundle = getIntent().getExtras();
        data = bundle.getInt("data",0);
        if(data != 10){

            txtNama1.setText(bundle.getString("nama"));

            if (bundle.getString("jenis_kelamin").equalsIgnoreCase("pria")) {
                rbPria1.setChecked(true);
                rbWanita1.setChecked(false);
            } else if (bundle.getString("jenis_kelamin").equalsIgnoreCase("Wanita")) {
                rbPria1.setChecked(false);
                rbWanita1.setChecked(true);

            } else {

                rbPria1.setChecked(false);
                rbWanita1.setChecked(false);
            }

            List<String> lstPekerjaan = Arrays.asList(getResources().getStringArray(R.array.pekerjaan));
            for (int x = 0; x < lstPekerjaan.size(); x++) {

                if (lstPekerjaan.get(x).equalsIgnoreCase(bundle.getString("pekerjaan"))) {
                    spnPekerjaan1.setSelection(x);
                }

            }

            tanggal1 = bundle.getString("tanggal_lahir");
            Date dateDummy = null;
            try {
                dateDummy =new SimpleDateFormat("dd-MMMM-yyyy").parse(tanggal1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendarLahir1.setDate(dateDummy.getTime());

            txtAlamat1.setText(bundle.getString("alamat"));
            txtEmail1.setText(bundle.getString("email"));
            txtCatatan1.setText(bundle.getString("catatan"));
            txtTelepon1.setText(bundle.getString("telepon"));
       }

//            return biodata;


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
        alertDialog.setMessage("Data Diperbarui").setIcon(R.drawable.ic_tick).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }




}