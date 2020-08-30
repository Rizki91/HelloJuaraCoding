package com.example.hellojuaracoding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity {

    String username,password;
    TextView txtUsername;
    ImageView btnTambahData,btnKeluar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        txtUsername = findViewById(R.id.txtUsername);
        btnTambahData = findViewById(R.id.btnTambahData);
        btnKeluar = findViewById(R.id.btnExit);
        mAuth = FirebaseAuth.getInstance();


        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("Password");


        txtUsername.setText(username);


        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                if (mAuth.getCurrentUser() == null);
                {
                    startActivity( new Intent(MainMenu.this, MainActivity.class));
                    finish();
                }
            }
        });

    }



    public  void tambah (View view){
        Intent in = new Intent(MainMenu.this,TambahDataSqlite .class);
        startActivity(in);

    }
    public void screenListData(View view) {
        Intent intent = new Intent(MainMenu.this, ListBiodata.class);
        startActivity(intent);
    }




}
