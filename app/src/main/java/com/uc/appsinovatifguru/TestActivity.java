package com.uc.appsinovatifguru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.uc.appsinovatifguru.Adapter.TestRecyclerViewAdapter;
import com.uc.appsinovatifguru.Listener.SoalListener;
import com.uc.appsinovatifguru.Model.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements SoalListener {

    private TextView test_title;
    private ImageButton test_back;
    private RecyclerView test_recyclerview;
    private TestRecyclerViewAdapter SoalAdapter;
    private ArrayList<Test> listSoal;
    private String variabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        getSoalFromDB();
        setListener();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Toast.makeText(TestActivity.this, "Maaf, waktu pengerjaan anda telah melebihi 10 menit.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, 10000);
    }

    private void setListener() {
        test_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getSoalFromDB() {
        String url = GlobalValue.serverURL+"testSoal/1";
        RequestQueue myQueue = Volley.newRequestQueue(this);
        JSONObject parameter = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray temp1 = response.getJSONArray("data");

                            for (int i = 0; i < temp1.length(); i++) {
                                JSONObject temp2 = temp1.getJSONObject(i);
                                Test temp3 = new Test();
                                temp3.setId(temp2.getInt("id"));
                                temp3.setSoal(temp2.getString("soal"));
                                temp3.setJawabanA(temp2.getString("jawabanA"));
                                temp3.setJawabanB(temp2.getString("jawabanB"));
                                temp3.setJawabanC(temp2.getString("jawabanC"));
                                temp3.setJawabanD(temp2.getString("jawabanD"));
                                temp3.setJawabanE(temp2.getString("jawabanE"));
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
        test_title = findViewById(R.id. test_title);
        test_back = findViewById(R.id. test_back);
        test_recyclerview = findViewById(R.id. test_recyclerview);
        listSoal = new ArrayList<>();
        SoalAdapter = new TestRecyclerViewAdapter(listSoal, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        test_recyclerview.setLayoutManager(manager);
        test_recyclerview.setAdapter(SoalAdapter);
        variabel = getIntent().getStringExtra("variabel");
        test_title.setText(variabel);
    }

    @Override
    public void OnRadioClicked(int position, int nilai) {
//        listSoal.get(position).setNilai(nilai);
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
//                temp1.put("nilai", listSoal.get(i).getNilai());
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
                                if(variabel.equalsIgnoreCase("Perilaku Inovatif Guru")){
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
