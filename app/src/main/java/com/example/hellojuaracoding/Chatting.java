package com.example.hellojuaracoding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import  android.widget.EditText;

import com.example.hellojuaracoding.adapter.AdapterListChat;
import com.example.hellojuaracoding.model.ChatModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chatting extends AppCompatActivity {

    RecyclerView lstChat;
    Button btnSendChat;
    EditText txtChat;
    AdapterListChat adapter;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        txtChat = findViewById(R.id.txtChat);
        btnSendChat = findViewById(R.id.btnSendChat);
        lstChat = findViewById(R.id.lstChat);

        btnSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtChat.getText().toString().equalsIgnoreCase("")){

                    Date dNow = new Date( );
                    SimpleDateFormat ft =
                            new SimpleDateFormat ("dd-MM-yyyy hh:mm:ss");



                    sendChatToFirebase("Fahrul",txtChat.getText().toString(),ft.format(dNow));
                }

            }
        });

    }

    List<ChatModel> lstDataChat = new ArrayList<ChatModel>();
    public void sendChatToFirebase (String username, String pesan, String tanggal){

        ChatModel chat = new ChatModel();
        chat.setNama(username);
        chat.setPesan(pesan);
        chat.setTanggal(tanggal);




        mDatabase.child("room").child(tanggal ).setValue(chat);



        mDatabase.child("room").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstDataChat.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    ChatModel user = postSnapshot.getValue(ChatModel.class);
                    lstDataChat.add(user);

                    adapter = new AdapterListChat(Chatting.this,lstDataChat);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lstChat.setLayoutManager(new LinearLayoutManager(Chatting.this));
                            lstChat.setItemAnimator(new DefaultItemAnimator());
                            lstChat.setAdapter(adapter);
                        }});


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


}