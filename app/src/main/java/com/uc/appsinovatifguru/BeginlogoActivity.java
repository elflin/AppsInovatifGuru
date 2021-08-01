package com.uc.appsinovatifguru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class BeginlogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginlogo);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                checkOnboarding();
                finish();
            }
        }, 2 * 1000);
    }


    private void checkOnboarding(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Check if we need to display our OnboardingSupportFragment
        if (!sharedPreferences.getBoolean(GlobalValue.Onboarding_Complete, false)) {
            // The user hasn't seen the OnboardingSupportFragment yet, so show it
            startActivity(new Intent(this, OnboardingActivity.class));
        }else{
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}