package com.example.hellojuaracoding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import  android.widget.EditText;

import com.example.hellojuaracoding.adapter.AdapterListChat;
import com.example.hellojuaracoding.model.AllMethods;
import com.example.hellojuaracoding.model.ChatModel;
import com.example.hellojuaracoding.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chatting extends AppCompatActivity implements  View.OnClickListener {

    private FirebaseAuth mAuth;
    RecyclerView lstChat;
    Button btnSendChat;
    EditText txtChat;
    AdapterListChat adapter;
    UserModel u;
    List <ChatModel> chat1 ;
    public DatabaseReference mDatabase;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        init();

//    List<ChatModel> lstDataChat = new ArrayList<ChatModel>();
//    public void sendChatToFirebase ( String nama,String pesan, String tanggal){
//
//        ChatModel chat1 = new ChatModel();
//
//        Bundle bundle = new Bundle();
//        bundle.putString("nama",nama);
////        chat1.setNama(nama);
//        chat1.setPesan(pesan);
//        chat1.setTanggal(tanggal);
//
//
//
//
//        String getUserID = mAuth.getCurrentUser().getUid();
////        String getNama = mAuth.getCurrentUser().getDisplayName();
//
//        mDatabase.child("Room").child(getUserID).child(tanggal ).setValue(chat1);
//
//
//
//        mDatabase.child("Room").child(getUserID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                lstDataChat.clear();
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//
//                    ChatModel user = postSnapshot.getValue(ChatModel.class);
//                    lstDataChat.add(user);
//
//                    adapter = new AdapterListChat(Chatting.this,lstDataChat);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            lstChat.setLayoutManager(new LinearLayoutManager(Chatting.this));
//                            lstChat.setItemAnimator(new DefaultItemAnimator());
//                            lstChat.setAdapter(adapter);
//                        }});
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//
//    }
    }
    public  void init(){
        mAuth = FirebaseAuth.getInstance();
        u = new UserModel();
        database = FirebaseDatabase.getInstance();
        txtChat = findViewById(R.id.txtChat);
        btnSendChat = findViewById(R.id.btnSendChat);
        lstChat = findViewById(R.id.lstChat);
        chat1 = new ArrayList<>();
        btnSendChat.setOnClickListener(this);

    }
        @Override
        public void onClick(View v) {
        if(!TextUtils.isEmpty(txtChat.getText().toString())){

            Date dNow = new Date ();
            SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");


            ChatModel chatModel = new ChatModel(u.getNama(),txtChat.getText().toString(),ft.format(dNow));

            txtChat.setText("");
            mDatabase.push().setValue(chatModel);
        }

        }



    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseUser  use = mAuth.getCurrentUser();

        u.setUid(use.getUid());
        u.setEmail(use.getEmail());

        database.getReference().child("Users").child(use.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                u = dataSnapshot.getValue(UserModel.class);
                u.setUid(use.getUid());
                AllMethods.nama = u.getNama();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase = database.getReference("messages");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ChatModel chat = dataSnapshot.getValue(ChatModel.class);
                UserModel user = dataSnapshot.getValue(UserModel.class);

                chat.setKey(dataSnapshot.getKey());

                chat1.add(chat);
                adapter = new AdapterListChat(Chatting.this,chat1,mDatabase);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lstChat.setLayoutManager(new LinearLayoutManager(Chatting.this));
                            lstChat.setItemAnimator(new DefaultItemAnimator());
                            lstChat.setAdapter(adapter);
                        }});
            }



            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);
                chatModel.setKey(dataSnapshot.getKey());

                List<ChatModel> newchatmodel = new ArrayList<ChatModel>();
                for (ChatModel n : chat1){
                    if(n.getKey().equals(chatModel.getKey())){
                        newchatmodel.add(chatModel);
                    }else {
                        newchatmodel.add(n);
                    }

                }

                chat1 = newchatmodel;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lstChat.setLayoutManager(new LinearLayoutManager(Chatting.this));
                        lstChat.setItemAnimator(new DefaultItemAnimator());
                        lstChat.setAdapter(adapter);
                    }});


            }


            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);
                chatModel.setKey(dataSnapshot.getKey());
                List<ChatModel> newchatModel = new ArrayList<ChatModel>();
                for (ChatModel n : chat1){
                    if(!n.getKey().equals(chatModel.getKey())){
                        newchatModel.add(chatModel);
                    }else {
                        newchatModel.add(n);
                    }
                }

                chat1 = newchatModel;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lstChat.setLayoutManager(new LinearLayoutManager(Chatting.this));
                        lstChat.setItemAnimator(new DefaultItemAnimator());
                        lstChat.setAdapter(adapter);
                    }});





            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        chat1 = new ArrayList<>();


    }

    private void displayMessages(List<ChatModel> chat1) {
        adapter = new AdapterListChat(Chatting.this,chat1,mDatabase);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lstChat.setLayoutManager(new LinearLayoutManager(Chatting.this));
                lstChat.setItemAnimator(new DefaultItemAnimator());
                lstChat.setAdapter(adapter);
            }});


    }


}

