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
import com.uc.appsinovatifguru.Listener.TestListener;
import com.uc.appsinovatifguru.Model.Test;
import com.uc.appsinovatifguru.R;

import java.util.ArrayList;
import java.util.Collections;

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.EvalRecyclerViewHolder>{
    private ArrayList<Test> listTest;
    private TestListener testListener;
    ArrayList<String> shuffleJawaban;
    private boolean alreadyShuffle = false;

    public TestRecyclerViewAdapter(ArrayList<Test> listTest, TestListener testListener){
        this.listTest = listTest;
        this.testListener = testListener;
    }

    @NonNull
    @Override
    public EvalRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.itemrv_soal, parent, false);
        alreadyShuffle = false;
        return new TestRecyclerViewAdapter.EvalRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvalRecyclerViewHolder holder, int position) {
        holder.itemsoal_number.setText(String.valueOf(position+1)+".");
        holder.itemsoal_soal.setText(listTest.get(position).getSoal());
        if (position == listTest.size()-1){
            holder.itemsoal_selesai.setVisibility(View.VISIBLE);
        }else{
            holder.itemsoal_selesai.setVisibility(View.GONE);
        }

        shuffleJawaban = new ArrayList<>();
        shuffleJawaban.add(listTest.get(position).getJawabanA());
        shuffleJawaban.add(listTest.get(position).getJawabanB());
        shuffleJawaban.add(listTest.get(position).getJawabanC());
        shuffleJawaban.add(listTest.get(position).getJawabanD());
        shuffleJawaban.add(listTest.get(position).getJawabanE());

        if (!alreadyShuffle) {
            Collections.shuffle(shuffleJawaban);
            alreadyShuffle = true;
        }
        holder.radio_button_1.setText(shuffleJawaban.get(0));
        holder.radio_button_2.setText(shuffleJawaban.get(1));
        holder.radio_button_3.setText(shuffleJawaban.get(2));
        holder.radio_button_4.setText(shuffleJawaban.get(3));
        holder.radio_button_5.setText(shuffleJawaban.get(4));
        listTest.get(position).setJawabanA(shuffleJawaban.get(0));
        listTest.get(position).setJawabanB(shuffleJawaban.get(1));
        listTest.get(position).setJawabanC(shuffleJawaban.get(2));
        listTest.get(position).setJawabanD(shuffleJawaban.get(3));
        listTest.get(position).setJawabanE(shuffleJawaban.get(4));
    }

    @Override
    public int getItemCount() {
        return listTest.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class EvalRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView itemsoal_number, itemsoal_soal;
        private RadioGroup itemsoal_radio;
        private RadioButton radio_button_1, radio_button_2, radio_button_3, radio_button_4, radio_button_5;
        private Button itemsoal_selesai;

        public EvalRecyclerViewHolder(@NonNull View itemView) {
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
                            testListener.OnRadioClicked(getAdapterPosition(), listTest.get(getAdapterPosition()).getJawabanA());
                            break;
                        case R.id.radio_button_2:
                            // do operations specific to this selection
                            testListener.OnRadioClicked(getAdapterPosition(), listTest.get(getAdapterPosition()).getJawabanB());
                            break;
                        case R.id.radio_button_3:
                            // do operations specific to this selection
                            testListener.OnRadioClicked(getAdapterPosition(), listTest.get(getAdapterPosition()).getJawabanC());
                            break;
                        case R.id.radio_button_4:
                            // do operations specific to this selection
                            testListener.OnRadioClicked(getAdapterPosition(), listTest.get(getAdapterPosition()).getJawabanD());
                            break;
                        case R.id.radio_button_5:
                            // do operations specific to this selection
                            testListener.OnRadioClicked(getAdapterPosition(), listTest.get(getAdapterPosition()).getJawabanE());
                            break;
                    }
                }
            });

            itemsoal_selesai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    testListener.OnSelesai();
                }
            });
        }
    }
}
