package com.example.hellojuaracoding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hellojuaracoding.model.ChatModel;
import com.example.hellojuaracoding.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DaftarUser extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText txtEmail, txtPassword,txtNama;
    Button btnDaftar;
    private ProgressDialog mProgres;
    private DatabaseReference mDatabase;
    ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_user);

        mAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtUsername1);
        txtPassword = findViewById(R.id.txtPassword1);
        txtNama = findViewById(R.id.txtNama);
        mProgres = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        btnDaftar = findViewById(R.id.btnDaftar);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {


                                             final String  displayNama = txtNama.getText().toString();
                                             final String Email = txtEmail.getText().toString();
                                             String Password = txtPassword.getText().toString();

                                             if (!TextUtils.isEmpty(displayNama) && !TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Password)) {
                                                 mProgres.setTitle("Daftra AKun");
                                                 mProgres.setMessage("Mohon Tunggu Daftar Akun");
                                                 mProgres.setCanceledOnTouchOutside(false);
                                                 mProgres.show();


                                             }

                                             mAuth.createUserWithEmailAndPassword(Email, Password)
                                                     .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<AuthResult> task) {
                                                             if (task.isSuccessful()) {
                                                                 // Sign in success, update UI with the signed-in user's information

                                                                 FirebaseUser user = mAuth.getCurrentUser();
                                                                 UserModel u = new UserModel();
                                                                 u.setNama(displayNama);
                                                                 u.setEmail(Email);

                                                                 mDatabase.child(user.getUid()).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                     @Override
                                                                     public void onComplete(@NonNull Task<Void> task) {
                                                                         if(task.isSuccessful()){
                                                                             Toast.makeText(getApplicationContext(),"Akun Sukses Didaftar", Toast.LENGTH_SHORT).show();
                                                                             progressBar.setVisibility(View.GONE);
                                                                             finish();
                                                                             Intent i = new Intent(DaftarUser.this,MainMenu.class);
                                                                             startActivity(i);
                                                                         }else {
                                                                             progressBar.setVisibility(View.GONE);
                                                                             Toast.makeText(getApplicationContext(),"Akun tidak terdaftar",Toast.LENGTH_SHORT).show();
                                                                         }
                                                                     }
                                                                 });


                                                                 finish();

                                                             } else {
                                                                 // If sign in fails, display a message to the user.

                                                                 Toast.makeText(DaftarUser.this, "Authentication failed.",
                                                                         Toast.LENGTH_SHORT).show();

                                                             }
                                                         }
                                                     });

                                         }
                                     });
    }

    private  ChatModel generalObject(){
        ChatModel chat = new ChatModel();

        return chat;
    }









//    private  void register_user(final String displayname,final  String Email, final String Password){
//        mAuth.createUserWithEmailAndPassword(Email,Password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if ((!task.isSuccessful())){
//                            mProgres.hide();
//
//                            String error = "";
//                            try {
//                                throw  task.getException();
//                            }catch (FirebaseAuthWeakPasswordException e){
//                                error = "Weak Passwrd";
//                            }catch (FirebaseAuthInvalidCredentialsException e){
//                                error = "Invalid Email";
//                            }catch (FirebaseAuthUserCollisionException e){
//                                error = "Existing Account";
//                            }catch (Exception e){
//                                error = "Unknoow Error !";
//                             e.printStackTrace();
//                            }
//                            Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
//                        }else {
//                            FirebaseUser currentUser = mAuth.getCurrentUser();
//                            String uid = currentUser.getUid();
//
//                            mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(uid);
//                            String divece_token = Firebese.getInstance().getToken();
//
//
//
//                        }
//                    }
//                });
//    }
//

}