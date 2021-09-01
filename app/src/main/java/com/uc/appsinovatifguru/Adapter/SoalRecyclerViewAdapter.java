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

import com.uc.appsinovatifguru.Listener.SoalListener;
import com.uc.appsinovatifguru.Model.Soal;
import com.uc.appsinovatifguru.R;

import java.util.ArrayList;

public class SoalRecyclerViewAdapter extends RecyclerView.Adapter<SoalRecyclerViewAdapter.SoalRecyclerViewHolder>{

    private ArrayList<Soal> listSoal;
    private SoalListener soalListener;
    private boolean isSubmit;

    private SoalRecyclerViewAdapter(ArrayList<Soal> listSoal, SoalListener soalListener, boolean isSubmit){
        this.listSoal = listSoal;
        this.soalListener = soalListener;
        this.isSubmit = isSubmit;
    }

    @NonNull
    @Override
    public SoalRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.itemrv_soal, parent, false);
        return new SoalRecyclerViewAdapter.SoalRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoalRecyclerViewHolder holder, int position) {
        holder.itemsoal_number.setText(String.valueOf(position+1)+".");
        holder.itemsoal_soal.setText(listSoal.get(position).getSoal());
        if (isSubmit){
            holder.itemsoal_selesai.setVisibility(View.VISIBLE);
        }else{
            holder.itemsoal_selesai.setVisibility(View.GONE);
        }
        if(listSoal.get(position).getUkuran().equalsIgnoreCase("sering")){
            holder.radio_button_1.setText("Sangat jarang");
            holder.radio_button_2.setText("Jarang");
            holder.radio_button_3.setText("Cukup");
            holder.radio_button_4.setText("Sering");
            holder.radio_button_5.setText("Sangat sering");
        }else if(listSoal.get(position).getUkuran().equalsIgnoreCase("setuju")){
            holder.radio_button_1.setText("Sangat tidak setuju");
            holder.radio_button_2.setText("Tidak setuju");
            holder.radio_button_3.setText("Cukup");
            holder.radio_button_4.setText("Setuju");
            holder.radio_button_5.setText("Sangat setuju");
        }else if(listSoal.get(position).getUkuran().equalsIgnoreCase("semua")){
            holder.radio_button_1.setText("Sangat tidak setuju");
            holder.radio_button_2.setText("Tidak setuju");
            holder.radio_button_3.setText("Cukup");
            holder.radio_button_4.setText("Setuju");
            holder.radio_button_5.setText("Sangat setuju");
        }else if(listSoal.get(position).getUkuran().equalsIgnoreCase("Terbukti")){
            holder.radio_button_1.setText("Sangat Jarang Terbukti");
            holder.radio_button_2.setText("Jarang Terbukti");
            holder.radio_button_3.setText("Cukup");
            holder.radio_button_4.setText("Sering Terbukti");
            holder.radio_button_5.setText("Sangat Sering Terbukti ");
        }else if(listSoal.get(position).getUkuran().equalsIgnoreCase("sesuai")){
            holder.radio_button_1.setText("Sangat tidak sesuai");
            holder.radio_button_2.setText("Tidak sesuai");
            holder.radio_button_3.setText("Cukup");
            holder.radio_button_4.setText("Sesuai");
            holder.radio_button_5.setText("Sangat sesuai");
        }

    }

    @Override
    public int getItemCount() {
        return listSoal.size();
    }

    public class SoalRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView itemsoal_number, itemsoal_soal;
        private RadioGroup itemsoal_radio;
        private RadioButton radio_button_1, radio_button_2, radio_button_3, radio_button_4, radio_button_5;
        private Button itemsoal_selesai;

        public SoalRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemsoal_number = itemView.findViewById(R.id.itemsoal_number);
            itemsoal_radio = itemView.findViewById(R.id.itemsoal_radio);
            itemsoal_soal = itemView.findViewById(R.id.itemsoal_soal);
            radio_button_1 = itemView.findViewById(R.id.radio_button_1);
            radio_button_2 = itemView.findViewById(R.id.radio_button_2);
            radio_button_3 = itemView.findViewById(R.id.radio_button_3);
            radio_button_4 = itemView.findViewById(R.id.radio_button_4);
            radio_button_5 = itemView.findViewById(R.id.radio_button_5);
            itemsoal_selesai = itemView.findViewById(R.id.itemsoal_selesai);

            itemsoal_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId){
                        case R.id.radio_button_1:
                            // do operations specific to this selection
                            soalListener.OnRadioClicked(getAdapterPosition(), 1);
                            break;
                        case R.id.radio_button_2:
                            // do operations specific to this selection
                            soalListener.OnRadioClicked(getAdapterPosition(), 2);
                            break;
                        case R.id.radio_button_3:
                            // do operations specific to this selection
                            soalListener.OnRadioClicked(getAdapterPosition(), 3);
                            break;
                        case R.id.radio_button_4:
                            // do operations specific to this selection
                            soalListener.OnRadioClicked(getAdapterPosition(), 4);
                            break;
                        case R.id.radio_button_5:
                            // do operations specific to this selection
                            soalListener.OnRadioClicked(getAdapterPosition(), 5);
                            break;
                    }
                }
            });

            itemsoal_selesai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    soalListener.OnSelesai();
                }
            });
        }
    }
}
