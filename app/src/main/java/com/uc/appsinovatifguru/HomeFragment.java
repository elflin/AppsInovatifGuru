package com.uc.appsinovatifguru;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uc.appsinovatifguru.Adapter.MainRecyclerViewAdapter;
import com.uc.appsinovatifguru.Listener.OnCardListener;
import com.uc.appsinovatifguru.Model.Menu;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnCardListener {

    private View view;
    private RecyclerView main_recyclerview;
    private ArrayList <Menu> listMenu;
    private MainRecyclerViewAdapter mainAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        Menu menu = new Menu("Survey", R.drawable.survey);
        menu.setNextClass(SurveymenuActivity.class);
        listMenu.add(menu);
        menu = new Menu("Training", R.drawable.training);
        menu.setNextClass(TrainingActivity.class);
        listMenu.add(menu);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        main_recyclerview.setLayoutManager(manager);
        main_recyclerview.setAdapter(mainAdapter);
        mainAdapter.notifyDataSetChanged();
    }

    private void initView() {
        main_recyclerview = view.findViewById(R.id.main_recyclerview);
        listMenu = new ArrayList<>();
        mainAdapter = new MainRecyclerViewAdapter(listMenu, this);
    }

    @Override
    public void onCardClick(int position) {
        startActivity(new Intent(getActivity(), listMenu.get(position).getNextClass()));
    }
}