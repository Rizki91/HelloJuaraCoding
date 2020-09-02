package com.example.hellojuaracoding;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hellojuaracoding.model.Biodata;
import com.example.hellojuaracoding.utility.SharedPrefUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TambahDataSqlite extends AppCompatActivity {
    EditText txtNama;
    TextView tvDateReselut;
    RadioButton rbPria,rbWanita;
    Spinner spnPekerjaan;
    CalendarView CalendarLahir;
    EditText txtAlamat,txtTelepon, txtEmail,txtCatatan;
    Button btnSimpan, btnBatal,btnTgl;
    String tanggal="";
    private AppDatabase mDb;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        txtNama = findViewById(R.id.txtNama);
        rbPria = findViewById(R.id.rbLaki);
        rbWanita = findViewById(R.id.rbPerempuan);
        spnPekerjaan = findViewById(R.id.spnPekerjaan);
        CalendarLahir = findViewById(R.id.txtCalender);
        txtAlamat = findViewById(R.id.txtalamat);
        txtTelepon = findViewById(R.id.txtTelpon);
        txtEmail = findViewById(R.id.txtEmail);
        txtCatatan = findViewById(R.id.txtCatatan);


        btnBatal = findViewById(R.id.btnBatal);
        btnSimpan = findViewById(R.id.btnSimpan);
        mAuth = FirebaseAuth.getInstance();

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDb = AppDatabase.getInstance(getApplicationContext());


        CalendarLahir.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
                Date date = new Date(year-1900,month,dayOfMonth);
                tanggal= sdf.format(date);
                Toast.makeText(TambahDataSqlite.this,tanggal ,Toast.LENGTH_LONG).show();
            }
        });


        String dataJson = SharedPrefUtil.getInstance(TambahDataSqlite.this).getString("data_input");

        if(!TextUtils.isEmpty(dataJson)){
//            updateBiodata(generateObjectData());

//            mappingData(dataJson);
        }else{

        }
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

//    private void updateBiodata(final Biodata biodata){
//        new AsyncTask<Void, Void, Long>(){
//            @Override
//            protected Long doInBackground(Void... voids) {
//                long status = mDb.biodataDao().updateBiodata(biodata);
//                return status;
//            }
//
//            @Override
//            protected void onPostExecute(Long status) {
//                Toast.makeText(TambahDataSqlite.this, "status row "+status, Toast.LENGTH_SHORT).show();
//            }
//        }.execute();
//    }

    public void mappingData(String json){
        Biodata biodata = new Gson().fromJson(json,Biodata.class);


        txtNama.setText(biodata.getNama());

        if(biodata.getJk().equalsIgnoreCase("Pria")){
            rbPria.setChecked(true);
            rbWanita.setChecked(false);
        }else if (biodata.getJk().equalsIgnoreCase("Wanita")){
            rbWanita.setChecked(true);
            rbPria.setChecked(false);
        }else{
            rbPria.setChecked(false);
            rbWanita.setChecked(false);
        }

        List<String> lstPekerjaan = Arrays.asList(getResources().getStringArray(R.array.pekerjaan));

        for(int x = 0 ;  x < lstPekerjaan.size();x++){

            if(lstPekerjaan.get(x).equalsIgnoreCase(biodata.getPekerjaan())){
                spnPekerjaan.setSelection(x);
            }

        }

        txtAlamat.setText(biodata.getAlamat());
        txtTelepon.setText(biodata.getTlp());
        txtEmail.setText(biodata.getEmail());
        txtCatatan.setText(biodata.getCatatan());


        Date dateDummy = null;
        try {
            dateDummy =new SimpleDateFormat("dd-MMMM-yyyy").parse(biodata.getTgl_lahir());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CalendarLahir.setDate(dateDummy.getTime());





    }

    public  List<Biodata> getModelArrayString(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Biodata>>(){}.getType();
        List <Biodata> biodataList = gson.fromJson(json, type);
        for(Biodata data : biodataList){
            Log.i("Contact Details", data.getNama() + "-" + data.getAlamat() + "-" + data.getEmail());

        }
        return  biodataList;
    }

    public boolean checkMandatory(){

        boolean pass = true;
        if(TextUtils.isEmpty(txtNama.getText().toString())){
            pass = false;
            txtNama.setError("Masukan nama, mandatory");

        }



        if(TextUtils.isEmpty(txtEmail.getText().toString() )|| !Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()){
            pass = false;
            txtEmail.setError("Masukan email dengan format yang benar");
        }

        return pass;
    }

    public void simpan(View view){
        if(checkMandatory()){

          new Thread(new Runnable() {
              @Override
              public void run() {
                  Biodata biodata = null ;
                  biodata = mDb.biodataDao().findByTelepon(txtTelepon.getText().toString());


                  if (biodata != null){
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              showErrorDialog2();

                          }
                      });
                  }else {

                      mDb.biodataDao().insertAll(generateObjectData());
                      String getUserID = mAuth.getCurrentUser().getUid();
                      mDatabase.child("Biodata").child(getUserID).child("Data").child(generateObjectData().getTlp()).setValue(generateObjectData());

//                      updateBiodata(generateObjectData());

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


    public  Biodata generateObjectData(){


        Biodata biodata = new Biodata();
        biodata.setNama(txtNama.getText().toString());

        if (rbPria.isChecked()){
            biodata.setJk("Pria");

        }else if (rbWanita.isChecked()){
            biodata.setJk("Wanita");
        }else{
            biodata.setJk("Tidak diketahui");
        }

        biodata.setPekerjaan(spnPekerjaan.getSelectedItem().toString());
        biodata.setTgl_lahir(tanggal);
        biodata.setAlamat(txtAlamat.getText().toString());
        biodata.setEmail(txtEmail.getText().toString());
        biodata.setTlp(txtTelepon.getText().toString());
        biodata.setCatatan(txtCatatan.getText().toString());

        return  biodata;

    }



    public void showErrorDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TambahDataSqlite.this);
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage("Mohon isi field yang mandatory").setIcon(R.drawable.ic_close).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(TambahDataSqlite.this,"Cancel ditekan",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }
    public void showErrorDialog2(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TambahDataSqlite.this);
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage("Mohon masukan telepon yang berbeda").setIcon(R.drawable.ic_close).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(TambahDataSqlite.this,"Cancel ditekan",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }


    public void showJsonDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(TambahDataSqlite.this);
        alertDialog.setTitle("Sukses");
        alertDialog.setMessage("Data Tersimpan").setIcon(R.drawable.ic_tick).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, TambahDataSqlite.class);
    }
}
