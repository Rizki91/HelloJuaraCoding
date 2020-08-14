package com.example.hellojuaracoding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellojuaracoding.R;
import com.example.hellojuaracoding.model.Biodata;


import java.util.ArrayList;
import java.util.List;
public class AdapterListBasic extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Biodata> items = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;


    int gambar [] = {R.drawable.ic_about, R.drawable.ic_checklist};



    public  interface  OnItemClickListener{
        void onItemClick(View view, Biodata obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListBasic(Context context, List<Biodata> items) {
        this.items = items;
        ctx = context;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            Biodata biodata = items.get(position);

            int i = position % gambar.length;
            view.image.setImageResource(gambar[i]);
            view.txtNama.setText(biodata.getNama());
            view.txtAlamat.setText(biodata.getAlamat());
            view.txtTelepon.setText(biodata.getTlp());
            view.txtPekerjaan.setText(biodata.getPekerjaan());
            view.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}