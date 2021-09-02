package com.uc.appsinovatifguru.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.appsinovatifguru.Model.Result;
import com.uc.appsinovatifguru.R;

import java.util.ArrayList;

public class ResultRecyclerViewAdapter extends RecyclerView.Adapter<ResultRecyclerViewAdapter.ResultRecyclerViewHolder>{

    private ArrayList<Result> listResult;

    public ResultRecyclerViewAdapter(ArrayList<Result> listResult){
        this.listResult = listResult;
    }

    @NonNull
    @Override
    public ResultRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.itemrv_result, parent, false);
        return new ResultRecyclerViewAdapter.ResultRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultRecyclerViewHolder holder, int position) {
        holder.resultcard_tanggal.setText(listResult.get(position).getTanggal());
        holder.resultcard_nilai1.setText(listResult.get(position).getNilai1());
        holder.resultcard_nilai2.setText(listResult.get(position).getNilai2());
        holder.resultcard_nilai3.setText(listResult.get(position).getNilai3());
        holder.resultcard_nilai4.setText(listResult.get(position).getNilai4());
        holder.resultcard_nilai5.setText(listResult.get(position).getNilai5());
        holder.resultcard_nilai6.setText(listResult.get(position).getNilai6());
        holder.resultcard_nilai7.setText(listResult.get(position).getNilai7());
    }

    @Override
    public int getItemCount() {
        return listResult.size();
    }

    public class ResultRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView resultcard_tanggal,
                resultcard_nilai1,
                resultcard_nilai2,
                resultcard_nilai3,
                resultcard_nilai4,
                resultcard_nilai5,
                resultcard_nilai6,
                resultcard_nilai7;

        public ResultRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            resultcard_tanggal = itemView.findViewById(R.id.resultcard_tanggal);
            resultcard_nilai1 = itemView.findViewById(R.id.nilai1);
            resultcard_nilai2 = itemView.findViewById(R.id.resultcard_nilai2);
            resultcard_nilai3 = itemView.findViewById(R.id.resultcard_nilai3);
            resultcard_nilai4 = itemView.findViewById(R.id.resultcard_nilai4);
            resultcard_nilai5 = itemView.findViewById(R.id.resultcard_nilai5);
            resultcard_nilai6 = itemView.findViewById(R.id.resultcard_nilai6);
            resultcard_nilai7 = itemView.findViewById(R.id.resultcard_nilai7);
        }
    }
}
