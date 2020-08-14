package com.example.hellojuaracoding;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txtusername);
        txtPassword = findViewById(R.id.txtpassword);
        btnLogin = findViewById(R.id.btnlogin);

        txtUsername.setText("FahrulRizki");
        txtPassword.setText("123456");

    }
    public  void login(View view){
        String pesan = "isinya adalah "+ txtUsername.getText().toString()+" Passwordnya adalah "+ txtPassword.getText().toString();
        Toast.makeText(MainActivity.this,pesan,Toast.LENGTH_LONG).show();

        Intent nexScreen = new Intent(MainActivity.this,MainMenu.class);
        nexScreen.putExtra("username",txtUsername.getText().toString());
        nexScreen.putExtra("password",txtPassword.getText().toString());

        startActivity(nexScreen);
    }
}
