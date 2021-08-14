package com.uc.appsinovatifguru;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private View view;
    private Button profile_signout;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initComponent();
        setListener();
        return view;
    }

    private void setListener() {
        profile_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                profile_signout.setVisibility(View.GONE);
                getActivity().finish();
                startActivity(new Intent(getContext(), BeginlogoActivity.class));
            }
        });
    }

    private void initComponent() {
        profile_signout = view.findViewById(R.id.profile_signout);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            profile_signout.setVisibility(View.VISIBLE);
        }else {
            profile_signout.setVisibility(View.GONE);
        }
    }
}