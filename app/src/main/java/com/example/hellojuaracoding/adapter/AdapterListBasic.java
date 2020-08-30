package com.example.hellojuaracoding.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.hellojuaracoding.AppDatabase;
import com.example.hellojuaracoding.EditData;
import com.example.hellojuaracoding.ListBiodata;
import com.example.hellojuaracoding.R;
import com.example.hellojuaracoding.TambahDataSqlite;
import com.example.hellojuaracoding.model.Biodata;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class AdapterListBasic extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Biodata> items;

    private AppDatabase mDb;
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private  int data;


    int gambar [] = {R.drawable.ic_about, R.drawable.ic_checklist};



    public AdapterListBasic() {

    }


    public  interface  OnItemClickListener{
        void onItemClick(@NonNull View view, Biodata obj, int position);

//        void OnDeletData(Biodata biodata, int position);

    }

    OnItemClickListener listener;


    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;

    }

    public AdapterListBasic(Context context, final ArrayList<Biodata> items) {
        this.items = items;

        ctx = context;
        mDb = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "biodata_db").allowMainThreadQueries().build();
        mOnItemClickListener = (ListBiodata)context;



    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView txtNama;
        public TextView txtAlamat;
        public TextView txtTelepon;
        public TextView txtPekerjaan;
        public CardView parentLayout;








        public OriginalViewHolder(View v) {
            super(v);


            image = v.findViewById(R.id.imgBiodata);
            txtNama = v.findViewById(R.id.txtNama);
            txtAlamat = v.findViewById(R.id.txtAlamat);
            txtPekerjaan = v.findViewById(R.id.txtPekerjaan);
            txtTelepon = v.findViewById(R.id.txtTelepon);
            parentLayout = v.findViewById(R.id.layout_utama);







        }

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {



        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            mDatabase = FirebaseDatabase.getInstance().getReference();

            final Biodata biodata = items.get(position);

            int i = position % gambar.length;
            view.image.setImageResource(gambar[i]);
            view.txtNama.setText(biodata.getNama());
            view.txtAlamat.setText(biodata.getAlamat());
            view.txtTelepon.setText(biodata.getTlp());
            view.txtPekerjaan.setText(biodata.getPekerjaan());
            view.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                        CharSequence [] menupilih = {"Edit", "Delet"};
                        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                        dialog.setTitle("Pilih Aksi");
                        dialog.setItems(menupilih, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:



                                        Bundle bundle = new Bundle();

                                        bundle.putInt("data", 0);
                                        bundle.putString("nama", items.get(position).getNama());
                                        bundle.putString("jenis_kelamin", items.get(position).getJk());
                                        bundle.putString("pekerjaan", items.get(position).getPekerjaan());
                                        bundle.putString("tanggal_lahir", items.get(position).getTgl_lahir());
                                        bundle.putString("alamat", items.get(position).getAlamat());
                                        bundle.putString("telepon", items.get(position).getTlp());
                                        bundle.putString("email", items.get(position).getEmail());
                                        bundle.putString("catatan", items.get(position).getCatatan());
                                        Intent intent = new Intent(view.getContext(),EditData.class);
                                        intent.putExtras(bundle);
//                                        bundle.putString("getPrimaryKey", items.get(position).getKey());
                                        ctx.startActivity(intent);


                                        break;

                                    case 1:
                                        if (mOnItemClickListener != null) {
                                            mOnItemClickListener.onItemClick(view, items.get(position), position);
                                        }
                                        mDb.biodataDao().deleteBiodata(items.get(position));
                                        items.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeRemoved(position, items.size());

//                                                DatabaseReference dbMahasiswa = mDatabase.child("Biodata").child("Data");
//                                                dbMahasiswa.removeValue();

                                        break;
                                }
                            }
                        });
                        dialog.create();
                        dialog.show();



                }
            });

        }
    }





    @Override
    public int getItemCount() {
        return items.size();
    }


//    private void onEditData(int position){
//        ctx.startActivity(new Intent(ctx, TambahDataSqlite.class).putExtra("data", (Parcelable) items.get(position)));
//        ((Activity)ctx).finish();
//
//    }

//    private void onDeteleBiodata(int position){
//        Biodata.BiodataDao().deletBiodata(tt.get(position));
//        daftarBarang.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeRemoved(position, daftarBarang.size());
//    }

//    private  void OnEdit(int position, Context ctx){
//        ctx.startActivity(new Intent(ctx,EditData.class).putExtra("data", (Serializable) items.get(position)));
//        ((Activity)ctx).finish();
//    }


}
