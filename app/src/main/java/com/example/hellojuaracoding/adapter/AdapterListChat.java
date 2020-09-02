package com.example.hellojuaracoding.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellojuaracoding.model.AllMethods;
import com.example.hellojuaracoding.model.ChatModel;
import com.example.hellojuaracoding.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;

public class AdapterListChat extends  RecyclerView.Adapter<AdapterListChat.AdapterListChatViewHolder> {

    Context context;
    List<ChatModel> chatModel;
    DatabaseReference mDatabase;


    public AdapterListChat (Context context,List<ChatModel>chatModel,DatabaseReference mDatabase){
        this.context= context;
        this.chatModel = chatModel;
        this.mDatabase = mDatabase;
    }



    @Override
    public AdapterListChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchat, parent, false);
        return new AdapterListChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterListChatViewHolder holder, int position) {

        ChatModel chat = chatModel.get(position);

        if(chat.getNama().equals(AllMethods.nama)){

            holder.linerLayt.setBackgroundResource(R.drawable.bg_chat1);
            holder.tvtTitel.setText("You : " + chat.getPesan() );
            holder.tvtTitel.setGravity(Gravity.START);
            holder.txtTimestamp.setText(chat.getTanggal());
            

        }else {
            holder.tvtTitel.setText(chat.getNama() + ":" +chat.getPesan());
            holder.linerLayt.setBackgroundResource(R.drawable.bg_chat2);
            holder.txtTimestamp.setText(chat.getTanggal());
            holder.btn_hapus.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {



        return chatModel.size();
    }




    public class AdapterListChatViewHolder extends  RecyclerView.ViewHolder {

        TextView tvtTitel;
        TextView txtTimestamp;
        ImageButton btn_hapus;
        LinearLayout linerLayt;
        public AdapterListChatViewHolder(View itemView) {
            super(itemView);
            tvtTitel= (TextView) itemView.findViewById(R.id.txtPesan);
            txtTimestamp = (TextView) itemView.findViewById(R.id.txtTimestamp);
            btn_hapus = (ImageButton) itemView.findViewById(R.id.btn_Hapus);
            linerLayt = (LinearLayout) itemView.findViewById(R.id.linerLayt);

            btn_hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase.child(chatModel.get(getAdapterPosition()).getKey()).setValue(null);
                }
            });
        }
    }
}