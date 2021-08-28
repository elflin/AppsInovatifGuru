package com.uc.appsinovatifguru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uc.appsinovatifguru.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private TextView profile_title;
    private ImageButton profile_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        setupFramelayout();
        setlistener();
    }


    public void simpanClicked(){
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void setlistener() {
        profile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupFramelayout() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        profile_title.setText(title);
        if (title.equalsIgnoreCase("Akun Saya")){
            getSupportFragmentManager().beginTransaction().replace(R.id.profile_framelayout, new DetailakunFragment()).commit();
        }else if(title.equalsIgnoreCase("Data Saya")){
            getSupportFragmentManager().beginTransaction().replace(R.id.profile_framelayout, new DatasayaFragment()).commit();
        }
    }

    private void initView() {
        profile_title = findViewById(R.id.profile_title);
        profile_back = findViewById(R.id.profile_back);
    }


}