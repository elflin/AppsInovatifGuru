package com.uc.appsinovatifguru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uc.appsinovatifguru.Model.Soal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SurveymenuActivity extends AppCompatActivity {

    private androidx.appcompat.widget.AppCompatButton
            surveymenu_menu1,
            surveymenu_menu2,
            surveymenu_menu3,
            surveymenu_menu4,
            surveymenu_menu5,
            surveymenu_menu6,
            surveymenu_menu7,
            surveymenu_reset;

    private ImageButton surveymenu_back;
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveymenu);
        initView();
        checkprogress();
        setListener();
    }

    private void getHistoryIDFromDB(){
        String url = GlobalValue.serverURL+"createHistory";
        RequestQueue myQueue = Volley.newRequestQueue(this);
        JSONObject parameter = new JSONObject();
        try {
            parameter.put("id", currUser.getUid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("suksess")){
                                SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                sharedPreferencesEditor.putInt(GlobalValue.historyId, response.getInt("historyId"));
                                sharedPreferencesEditor.apply();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        myQueue.add(request);
    }

    private void checkprogress() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.getBoolean(GlobalValue.var1, false)&&
                !sharedPreferences.getBoolean(GlobalValue.var2, false)&&
                !sharedPreferences.getBoolean(GlobalValue.var3, false)&&
                !sharedPreferences.getBoolean(GlobalValue.var4, false)&&
                !sharedPreferences.getBoolean(GlobalValue.var5, false)&&
                !sharedPreferences.getBoolean(GlobalValue.var6, false)&&
                !sharedPreferences.getBoolean(GlobalValue.var7, false)) {
            surveymenu_reset.setVisibility(View.GONE);
            getHistoryIDFromDB();
        }

        if(sharedPreferences.getBoolean(GlobalValue.var1, false)){
            surveymenu_menu1.setBackgroundResource(R.drawable.green_button);
        }else{
            surveymenu_menu1.setBackgroundResource(R.drawable.gray_button);
        }
        if(sharedPreferences.getBoolean(GlobalValue.var2, false)){
            surveymenu_menu2.setBackgroundResource(R.drawable.green_button);
        }else{
            surveymenu_menu2.setBackgroundResource(R.drawable.gray_button);
        }
        if(sharedPreferences.getBoolean(GlobalValue.var3, false)){
            surveymenu_menu3.setBackgroundResource(R.drawable.green_button);
        }else{
            surveymenu_menu3.setBackgroundResource(R.drawable.gray_button);
        }
        if(sharedPreferences.getBoolean(GlobalValue.var4, false)){
            surveymenu_menu4.setBackgroundResource(R.drawable.green_button);
        }else{
            surveymenu_menu4.setBackgroundResource(R.drawable.gray_button);
        }
        if(sharedPreferences.getBoolean(GlobalValue.var5, false)){
            surveymenu_menu5.setBackgroundResource(R.drawable.green_button);
        }else{
            surveymenu_menu5.setBackgroundResource(R.drawable.gray_button);
        }
        if(sharedPreferences.getBoolean(GlobalValue.var6, false)){
            surveymenu_menu6.setBackgroundResource(R.drawable.green_button);
        }else{
            surveymenu_menu6.setBackgroundResource(R.drawable.gray_button);
        }
        if(sharedPreferences.getBoolean(GlobalValue.var7, false)){
            surveymenu_menu7.setBackgroundResource(R.drawable.green_button);
        }else{
            surveymenu_menu7.setBackgroundResource(R.drawable.gray_button);
        }

    }

    private boolean checker(String var){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean(var, false)){
            return true;
        }else{
            return false;
        }
    }

    private void setListener() {
        surveymenu_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Perilaku Inovatif Guru");
                intent.putExtra("isClicked", checker(GlobalValue.var1));
                startActivity(intent);
            }
        });

        surveymenu_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Intensi Berinovasi");
                intent.putExtra("isClicked", checker(GlobalValue.var2));
                startActivity(intent);
            }
        });

        surveymenu_menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Sikap Terhadap Inovasi");
                intent.putExtra("isClicked", checker(GlobalValue.var3));
                startActivity(intent);
            }
        });

        surveymenu_menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Norma Subyektif terhadap Kreativitas");
                intent.putExtra("isClicked", checker(GlobalValue.var4));
                startActivity(intent);
            }
        });

        surveymenu_menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Efikasi Berinovasi");
                intent.putExtra("isClicked", checker(GlobalValue.var5));
                startActivity(intent);
            }
        });

        surveymenu_menu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Budaya Organisasi Berorientasi Pembelajaran");
                intent.putExtra("isClicked", checker(GlobalValue.var6));
                startActivity(intent);
            }
        });

        surveymenu_menu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Self-Determination");
                intent.putExtra("isClicked", checker(GlobalValue.var7));
                startActivity(intent);
            }
        });

        surveymenu_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                sharedPreferencesEditor.putBoolean(GlobalValue.var1, false);
                sharedPreferencesEditor.putBoolean(GlobalValue.var2, false);
                sharedPreferencesEditor.putBoolean(GlobalValue.var3, false);
                sharedPreferencesEditor.putBoolean(GlobalValue.var4, false);
                sharedPreferencesEditor.putBoolean(GlobalValue.var5, false);
                sharedPreferencesEditor.putBoolean(GlobalValue.var6, false);
                sharedPreferencesEditor.putBoolean(GlobalValue.var7, false);
                sharedPreferencesEditor.apply();
                checkprogress();
            }
        });

        surveymenu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        surveymenu_menu1 = findViewById(R.id.surveymenu_menu1);
        surveymenu_menu2 = findViewById(R.id.surveymenu_menu2);
        surveymenu_menu3 = findViewById(R.id.surveymenu_menu3);
        surveymenu_menu4 = findViewById(R.id.surveymenu_menu4);
        surveymenu_menu5 = findViewById(R.id.surveymenu_menu5);
        surveymenu_menu6 = findViewById(R.id.surveymenu_menu6);
        surveymenu_menu7 = findViewById(R.id.surveymenu_menu7);
        surveymenu_reset = findViewById(R.id.surveymenu_reset);
        surveymenu_back = findViewById(R.id.surveymenu_back);
        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
    }
}