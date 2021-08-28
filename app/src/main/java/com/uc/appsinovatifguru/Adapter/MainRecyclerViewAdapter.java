package com.uc.appsinovatifguru.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.appsinovatifguru.Listener.OnCardListener;
import com.uc.appsinovatifguru.Model.Menu;
import com.uc.appsinovatifguru.R;

import java.util.ArrayList;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder>{

    private ArrayList<Menu> listMenu;
    private OnCardListener onCardListener;

    public MainRecyclerViewAdapter(ArrayList<Menu> listMenu, OnCardListener onCardListener){
        this.listMenu = listMenu;
        this.onCardListener = onCardListener;
    }

    @NonNull
    @Override
    public MainRecyclerViewAdapter.MainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.itemrv_main, parent, false);
        return new MainRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewAdapter.MainRecyclerViewHolder holder, int position) {
        holder.itemrv_main_menu.setText(listMenu.get(position).getName());
        holder.itemrv_main_image.setImageResource(listMenu.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return this.listMenu.size();
    }

    public class MainRecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemrv_main_image;
        private TextView itemrv_main_menu;

        public MainRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemrv_main_image = itemView.findViewById(R.id.itemrv_main_image);
            itemrv_main_menu = itemView.findViewById(R.id.itemrv_main_menu);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCardListener.onCardClick(getAdapterPosition());
                }
            });
        }
    }
}
