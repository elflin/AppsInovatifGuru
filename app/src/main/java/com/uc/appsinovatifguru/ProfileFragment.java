package com.uc.appsinovatifguru;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private View view;
    private Button profile_signout, profile_akunsaya, profile_datasaya;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initComponent();
        setListener();
        return view;
    }

    private void setListener() {

        ActivityResultLauncher<Intent> laucherActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.refreshData();
                        }
                    }
                });

        profile_akunsaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("title", "Akun Saya");
                laucherActivityResultLauncher.launch(intent);
            }
        });

        profile_datasaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("title", "Data Saya");
                laucherActivityResultLauncher.launch(intent);
            }
        });

        profile_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                getContext().getSharedPreferences(GlobalValue.historyId, 0).edit().clear().apply();
                getContext().getSharedPreferences(GlobalValue.progressHistoryId, 0).edit().clear().apply();
                SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                sharedPreferencesEditor.putBoolean(GlobalValue.var1, false);
                sharedPreferencesEditor.putBoolean(GlobalValue.var2, false);
                sharedPreferencesEditor.putBoolean(GlobalValue.var3, false);
                sharedPreferencesEditor.putBoolean(GlobalValue.var4, false);
                sharedPreferencesEditor.putBoolean(GlobalValue.var5, false);
                sharedPreferencesEditor.putBoolean(GlobalValue.var6, false);
                sharedPreferencesEditor.putBoolean(GlobalValue.var7, false);
                sharedPreferencesEditor.apply();
                getActivity().finish();
                startActivity(new Intent(getContext(), BeginlogoActivity.class));
            }
        });
    }

    private void initComponent() {
        profile_signout = view.findViewById(R.id.profile_signout);
        profile_akunsaya = view.findViewById(R.id.profile_akunsaya);
        profile_datasaya = view.findViewById(R.id.profile_datasaya);
        mAuth = FirebaseAuth.getInstance();
    }
}