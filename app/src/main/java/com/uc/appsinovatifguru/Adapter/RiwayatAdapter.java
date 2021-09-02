package com.uc.appsinovatifguru.Adapter;

import android.graphics.Color;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.appsinovatifguru.Model.Riwayat;
import com.uc.appsinovatifguru.R;

import java.util.ArrayList;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>{

    private ArrayList<Riwayat> listRiwayat;

    public RiwayatAdapter(ArrayList<Riwayat> listRiwayat){
        this.listRiwayat = listRiwayat;
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.itemrv_riwayat1, parent, false);
        return new RiwayatAdapter.RiwayatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder holder, int position) {
        if (listRiwayat.get(position).getRiwayat_nilai().equalsIgnoreCase("Sangat Tinggi")){
            holder.riwayat_image.setImageResource(R.drawable.sangat_tinggi);
            holder.riwayat_nilai.setTextColor(Color.parseColor("#21B230"));
        }else if (listRiwayat.get(position).getRiwayat_nilai().equalsIgnoreCase("Tinggi")){
            holder.riwayat_image.setImageResource(R.drawable.tinggi);
            holder.riwayat_nilai.setTextColor(Color.parseColor("#FB7402"));
        }else if (listRiwayat.get(position).getRiwayat_nilai().equalsIgnoreCase("Cukup")){
            holder.riwayat_image.setImageResource(R.drawable.sedang);
            holder.riwayat_nilai.setTextColor(Color.parseColor("#FFC041"));
        }else{
            holder.riwayat_image.setImageResource(R.drawable.sangatrendah_rendah);
            holder.riwayat_nilai.setTextColor(Color.parseColor("#6D6E6F"));
        }

        holder.riwayat_nilai.setText(listRiwayat.get(position).getRiwayat_nilai());
        holder.riwayat_tanggal.setText(listRiwayat.get(position).getTanggal());
        holder.riwayat_angka.setText(listRiwayat.get(position).getRiwayat_angka());


        holder.button_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.riwayat_expand.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(holder.riwayat_cardview, new AutoTransition());
                    holder.riwayat_expand.setVisibility(View.VISIBLE);
                    holder.button_switch.setImageResource(R.drawable.ic_up);
                    holder.dimensi1.setText(listRiwayat.get(position).getDimensi1());
                    holder.nilai1.setText(listRiwayat.get(position).getNilai1());

                    if(listRiwayat.get(position).getDimensi2()!=null) {
                        holder.dimensi2.setText(listRiwayat.get(position).getDimensi2());
                        holder.nilai2.setText(listRiwayat.get(position).getNilai2());
                        holder.layout2.setVisibility(View.VISIBLE);
                    }else{
                        holder.layout2.setVisibility(View.GONE);
                    }

                    if(listRiwayat.get(position).getDimensi3()!=null) {
                        holder.dimensi3.setText(listRiwayat.get(position).getDimensi3());
                        holder.nilai3.setText(listRiwayat.get(position).getNilai3());
                        holder.layout2.setVisibility(View.VISIBLE);
                    }else{
                        holder.layout3.setVisibility(View.GONE);
                    }

                    if(listRiwayat.get(position).getDimensi4()!=null) {
                        holder.dimensi4.setText(listRiwayat.get(position).getDimensi4());
                        holder.nilai4.setText(listRiwayat.get(position).getNilai4());
                        holder.layout2.setVisibility(View.VISIBLE);
                    }else{
                        holder.layout4.setVisibility(View.GONE);
                    }
                }else if (holder.riwayat_expand.getVisibility() == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(holder.riwayat_cardview, new AutoTransition());
                    holder.riwayat_expand.setVisibility(View.GONE);
                    holder.button_switch.setImageResource(R.drawable.ic_down);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRiwayat.size();
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder {

        private ImageView riwayat_image;
        private TextView riwayat_nilai, riwayat_tanggal, dimensi1, dimensi2, dimensi3, dimensi4, nilai1,
                nilai2,nilai3, nilai4, riwayat_angka;
        private ImageView button_switch;
        private ConstraintLayout riwayat_expand;
        private CardView riwayat_cardview;
        private LinearLayout layout2, layout3, layout4;

        public RiwayatViewHolder(@NonNull View itemView) {
            super(itemView);
            riwayat_image = itemView.findViewById(R.id.riwayat_image);
            riwayat_nilai = itemView.findViewById(R.id.riwayat_nilai);
            riwayat_tanggal = itemView.findViewById(R.id.riwayat_tanggal);
            dimensi1 = itemView.findViewById(R.id.dimensi1);
            dimensi2 = itemView.findViewById(R.id.dimensi2);
            dimensi3 = itemView.findViewById(R.id.dimensi3);
            dimensi4 = itemView.findViewById(R.id.dimensi4);
            nilai1 = itemView.findViewById(R.id.nilai1);
            nilai2 = itemView.findViewById(R.id.nilai2);
            nilai3 = itemView.findViewById(R.id.nilai3);
            nilai4 = itemView.findViewById(R.id.nilai4);
            riwayat_angka = itemView.findViewById(R.id.riwayat_angka);
            riwayat_expand = itemView.findViewById(R.id.riwayat_expand);
            button_switch =itemView.findViewById(R.id.button_switch);
            riwayat_cardview = itemView.findViewById(R.id.riwayat_cardview);
            layout2 = itemView.findViewById(R.id.layout2);
            layout3 = itemView.findViewById(R.id.layout3);
            layout4 = itemView.findViewById(R.id.layout4);
        }
    }
}
