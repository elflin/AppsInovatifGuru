package com.uc.appsinovatifguru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.uc.appsinovatifguru.Adapter.SoalRecyclerViewAdapter;
import com.uc.appsinovatifguru.Listener.SoalListener;
import com.uc.appsinovatifguru.Model.Soal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity implements SoalListener {

    private TextView quiz_title;
    private ImageButton quiz_back;
    private RecyclerView quiz_recyclerview;
    private SoalRecyclerViewAdapter SoalAdapter;
    private ArrayList<Soal> listSoal;
    private String variabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initView();
        getSoalFromDB();
        setListener();
    }

    private void setListener() {
        quiz_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getSoalFromDB() {
        String url = GlobalValue.serverURL+"getSoalBy";
        RequestQueue myQueue = Volley.newRequestQueue(this);
        JSONObject parameter = new JSONObject();
        try {
            parameter.put("variabel", variabel);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray temp1 = response.getJSONArray("data");

                            for (int i = 0; i < temp1.length(); i++) {
                                JSONObject temp2 = temp1.getJSONObject(i);
                                Soal temp3 = new Soal();
                                temp3.setId(temp2.getInt("id"));
                                temp3.setNo_item(temp2.getString("no_item"));
                                temp3.setVariabel(temp2.getString("variabel"));
                                temp3.setSoal(temp2.getString("soal"));
                                temp3.setDimensi(temp2.getString("dimensi"));
                                temp3.setUkuran(temp2.getString("ukuran"));
                                listSoal.add(temp3);
                            }
                            SoalAdapter.notifyDataSetChanged();
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

    private void initView() {
        quiz_title = findViewById(R.id. quiz_title);
        quiz_back = findViewById(R.id. quiz_back);
        quiz_recyclerview = findViewById(R.id. quiz_recyclerview);
        listSoal = new ArrayList<>();
        SoalAdapter = new SoalRecyclerViewAdapter(listSoal, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        quiz_recyclerview.setLayoutManager(manager);
        quiz_recyclerview.setAdapter(SoalAdapter);
        variabel = getIntent().getStringExtra("variabel");
        quiz_title.setText(variabel);
    }

    @Override
    public void OnRadioClicked(int position, int nilai) {
        listSoal.get(position).setNilai(nilai);
    }

    @Override
    public void OnSelesai() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int historyId = sharedPreferences.getInt(GlobalValue.historyId, -1);
        String url = GlobalValue.serverURL+"insertJawabanByHistory";
        RequestQueue myQueue = Volley.newRequestQueue(this);

        JSONArray parameter1 = new JSONArray();
        for (int i = 0; i< listSoal.size(); i++){
            JSONObject temp1 = new JSONObject();
            try {
                temp1.put("historyId", historyId);
                temp1.put("soalId", listSoal.get(i).getId());
                temp1.put("nilai", listSoal.get(i).getNilai());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            parameter1.put(temp1);
        }

        JSONObject parameter = new JSONObject();
        try {
            parameter.put("data", parameter1);
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
                                if(variabel.equalsIgnoreCase("Perilaku Inovartif Guru")){
                                    sharedPreferencesEditor.putBoolean(GlobalValue.var1, true);
                                }else if(variabel.equalsIgnoreCase("Intensi Berinovasi")){
                                    sharedPreferencesEditor.putBoolean(GlobalValue.var2, true);
                                }else if(variabel.equalsIgnoreCase("Sikap Terhadap Inovasi")){
                                    sharedPreferencesEditor.putBoolean(GlobalValue.var3, true);
                                }else if(variabel.equalsIgnoreCase("Norma Subyektif terhadap Kreativitas")){
                                    sharedPreferencesEditor.putBoolean(GlobalValue.var4, true);
                                }else if(variabel.equalsIgnoreCase("Efikasi Berinovasi")){
                                    sharedPreferencesEditor.putBoolean(GlobalValue.var5, true);
                                }else if(variabel.equalsIgnoreCase("Budaya Organisasi Berorientasi Pembelajaran")){
                                    sharedPreferencesEditor.putBoolean(GlobalValue.var6, true);
                                }else if(variabel.equalsIgnoreCase("Self-Determination")){
                                    sharedPreferencesEditor.putBoolean(GlobalValue.var7, true);
                                }
                                sharedPreferencesEditor.apply();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
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
}