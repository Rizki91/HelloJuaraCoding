package com.example.hellojuaracoding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hellojuaracoding.adapter.AdapterListBasic;
import com.example.hellojuaracoding.model.Biodata;
import com.example.hellojuaracoding.utility.SharedPrefUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListBiodata extends AppCompatActivity implements AdapterListBasic.OnItemClickListener {

    RecyclerView lstBiodata;
    private AppDatabase mDb;
    private Button btnCari;
    private EditText txtCari;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    String textCari;

    private ArrayList<Biodata> biodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_biodata);

        txtCari = findViewById(R.id.txtCari);
        btnCari = findViewById(R.id.btnCari);
        lstBiodata = findViewById(R.id.lstBiodata);
        mDb = AppDatabase.getInstance(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference("Biodata");

        GetData();

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCari = txtCari.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loadDatabase(textCari);
                    }
                }).start();;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadDatabase();
            }
        }).start();




    }

    public List<Biodata> loadData(){
        List<Biodata> biodataList =null;
        if(!SharedPrefUtil.getInstance(ListBiodata.this).getString("data_input").isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Biodata>>(){}.getType();
            biodataList = gson.fromJson(SharedPrefUtil.getInstance(ListBiodata.this).getString("data_input"), type);
            for (Biodata data : biodataList){
                Log.i("Contact Details", data.getNama() + "-" + data.getAlamat() + "-" + data.getEmail());
            }

        }

        return biodataList;
    }

    public void loadDatabase(){
        ArrayList<Biodata> biodataList =null;
        biodataList = (ArrayList<Biodata>) mDb.biodataDao().getAll();
        adapter = new AdapterListBasic(ListBiodata.this,biodataList);
        adapter.setOnItemClickListener(ListBiodata.this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lstBiodata.setLayoutManager(new LinearLayoutManager(ListBiodata.this));
                lstBiodata.setItemAnimator(new DefaultItemAnimator());
                lstBiodata.setAdapter(adapter);
            }});
    }
    AdapterListBasic adapter;
    public void loadDatabase(String cari){
        ArrayList<Biodata> biodataList =null;
        biodataList = (ArrayList<Biodata>) mDb.biodataDao().findByNama(cari);
        adapter = new AdapterListBasic(ListBiodata.this,biodataList);
        adapter.setOnItemClickListener(ListBiodata.this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lstBiodata.setLayoutManager(new LinearLayoutManager(ListBiodata.this));
                lstBiodata.setItemAnimator(new DefaultItemAnimator());
                lstBiodata.setAdapter(adapter);
            }
        });

    }




    private  void GetData(){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Biodata").child(mAuth.getUid()).child("Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                biodata = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Biodata bdt = snapshot.getValue(Biodata.class);


                    bdt.setTlp(snapshot.getKey());
                    biodata.add(bdt);
                }

                adapter = new AdapterListBasic(ListBiodata.this, biodata);
                adapter.setOnItemClickListener(ListBiodata.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lstBiodata.setLayoutManager(new LinearLayoutManager(ListBiodata.this));
                        lstBiodata.setItemAnimator(new DefaultItemAnimator());
                        lstBiodata.setAdapter(adapter);
                    }});

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());

            }
        });
    }




    @Override
    public void onItemClick(View view ,final Biodata obj, int position) {
//        ImageView v = view.findViewById(R.id.imgBiodata);
//        v.setImageResource(R.drawable.ic_close);
//        lstBiodata.invalidate();

        if (mDatabase != null) {
            String userID = mAuth.getUid();
            mDatabase.child("Biodata")
                    .child(userID)
                    .child("Data")
                    .child(obj.getTlp())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ListBiodata.this, "Data Berhasil Hapus", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(ListBiodata.this, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
        }

    }


}