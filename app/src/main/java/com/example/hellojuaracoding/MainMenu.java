package com.example.hellojuaracoding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    String username,password;
    TextView txtUsername;
    ImageView btnTambahData,btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        txtUsername = findViewById(R.id.txtUsername);
        btnTambahData = findViewById(R.id.btnTambahData);
        btnExit = findViewById(R.id.btnExit);


        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("Password");


        txtUsername.setText(username);


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public  void tambah (View view){
        Intent in = new Intent(MainMenu.this,TambahData.class);
        startActivity(in);

    }
    public void screenListData(View view) {
        Intent intent = new Intent(MainMenu.this, ListBiodata.class);
        startActivity(intent);
    }
}
