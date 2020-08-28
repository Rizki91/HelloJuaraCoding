package com.example.hellojuaracoding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    RelativeLayout activity_main;
    Button btnLogin,btnLogin2;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txtusername);
        txtPassword = findViewById(R.id.txtpassword);
        btnLogin = findViewById(R.id.btnlogin);
        btnLogin2 = findViewById(R.id.btnlogin2);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (mAuth.getCurrentUser() != null)
            startActivity(new Intent(MainActivity.this, MainMenu.class));

//
//        txtUsername.setText("FahrulRizki");
//        txtPassword.setText("123456");

        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity. this, DaftarUser.class) );

            }
        });

    }
    public  void login(View view){
//        String pesan = "isinya adalah "+ txtUsername.getText().toString()+" Passwordnya adalah "+ txtPassword.getText().toString();
//        Toast.makeText(MainActivity.this,pesan,Toast.LENGTH_LONG).show();

//        mAuth.signInWithEmailAndPassword(txtUsername.getText().toString(), txtPassword.getText().toString())
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            String pesan = "Isinya adalah " + txtUsername.getText().toString() + "  passwordnya adalah " + txtPassword.getText().toString();
//                            Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_LONG).show();
//
//                            Intent nexScreen = new Intent(MainActivity.this,MainMenu.class);
//                            nexScreen.putExtra("username",txtUsername.getText().toString());
//                            nexScreen.putExtra("password",txtPassword.getText().toString());
//
//                            startActivity(nexScreen);
//
//                        }else {
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });

        loginUser (txtUsername.getText().toString(), txtPassword.getText().toString());

        }

    private void loginUser(String email, final String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                snackbar = Snackbar.make(activity_main, "Paswword length must be over 6", snackbar.LENGTH_SHORT);
                                snackbar.show();

                            }

                        } else {
                            startActivity(new Intent(MainActivity.this, MainMenu.class));
                        }

                    }
                });

    }


    }

