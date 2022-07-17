package com.uc.appsinovatifguru.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.uc.appsinovatifguru.Listener.EvalListener;
import com.uc.appsinovatifguru.Model.Eval;
import com.uc.appsinovatifguru.R;

import java.util.ArrayList;

public class EvalRecyclerViewAdapter extends RecyclerView.Adapter<EvalRecyclerViewAdapter.EvalRecyclerViewHolder>{

    private ArrayList<Eval> listPertanyaan;
    private EvalListener evalListener;


    public EvalRecyclerViewAdapter(ArrayList<Eval> listPertanyaan, EvalListener evalListener){
        this.listPertanyaan = listPertanyaan;
        this.evalListener = evalListener;
    }

    @NonNull
    @Override
    public EvalRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.itemrv_eval, parent, false);
        return new EvalRecyclerViewAdapter.EvalRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvalRecyclerViewHolder holder, int position) {
        holder.itemsoal_number.setText(String.valueOf(position+1)+".");
        holder.itemsoal_soal.setText(listPertanyaan.get(position).getPertanyaan());
        if (position == listPertanyaan.size()-1) {
            holder.itemsoal_selesai.setVisibility(View.VISIBLE);
            holder.itemeval_kesansarantext.setVisibility(View.VISIBLE);
            holder.itemsoal_kesansaran.setVisibility(View.VISIBLE);
        } else {
            holder.itemsoal_selesai.setVisibility(View.GONE);
            holder.itemeval_kesansarantext.setVisibility(View.GONE);
            holder.itemsoal_kesansaran.setVisibility(View.GONE);
        }
        holder.radio_button_1.setText("Sangat setuju");
        holder.radio_button_2.setText("Setuju");
        holder.radio_button_3.setText("Cukup");
        holder.radio_button_4.setText("Tidak setuju");
        holder.radio_button_5.setText("Sangat tidak setuju");
    }

    @Override
    public int getItemCount() {
        return listPertanyaan.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class EvalRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView itemsoal_number, itemsoal_soal, itemeval_kesansarantext;
        private RadioGroup itemsoal_radio;
        private RadioButton radio_button_1, radio_button_2, radio_button_3, radio_button_4, radio_button_5;
        private Button itemsoal_selesai;
        private TextInputLayout itemsoal_kesansaran;

        public EvalRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemsoal_number = itemView.findViewById(R.id.itemsoal_number);
            itemsoal_radio = itemView.findViewById(R.id.itemsoal_radio);
            itemeval_kesansarantext = itemView.findViewById(R.id.itemeval_kesansarantext);
            itemsoal_soal = itemView.findViewById(R.id.itemsoal_soal);
            radio_button_1 = itemView.findViewById(R.id.radio_button_1);
            radio_button_2 = itemView.findViewById(R.id.radio_button_2);
            radio_button_3 = itemView.findViewById(R.id.radio_button_3);
            radio_button_4 = itemView.findViewById(R.id.radio_button_4);
            radio_button_5 = itemView.findViewById(R.id.radio_button_5);
            itemsoal_selesai = itemView.findViewById(R.id.itemsoal_selesai);
            itemsoal_kesansaran = itemView.findViewById(R.id.itemsoal_kesansaran);

            itemsoal_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId){
                        case R.id.radio_button_1:
                            // do operations specific to this selection
                            evalListener.OnRadioClicked(getAdapterPosition(), 1);
                            break;
                        case R.id.radio_button_2:
                            // do operations specific to this selection
                            evalListener.OnRadioClicked(getAdapterPosition(), 2);
                            break;
                        case R.id.radio_button_3:
                            // do operations specific to this selection
                            evalListener.OnRadioClicked(getAdapterPosition(), 3);
                            break;
                        case R.id.radio_button_4:
                            // do operations specific to this selection
                            evalListener.OnRadioClicked(getAdapterPosition(), 4);
                            break;
                        case R.id.radio_button_5:
                            // do operations specific to this selection
                            evalListener.OnRadioClicked(getAdapterPosition(), 5);
                            break;
                    }
                }
            });

            itemsoal_selesai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    evalListener.OnSelesai(itemsoal_kesansaran.getEditText().getText().toString());
                }
            });
        }
    }
}
