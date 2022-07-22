package com.uc.appsinovatifguru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.uc.appsinovatifguru.Listener.TestListener;
import com.uc.appsinovatifguru.Model.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements TestListener {
    private TextView test_title;
    private ImageButton test_back;
    private RecyclerView test_recyclerview;
    private TestRecyclerViewAdapter SoalAdapter;
    private ArrayList<Test> listSoal;
    private String tipe;
    Handler handler = new Handler();
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        getSoalFromDB();
        setListener();

        handler.postDelayed(new Runnable() {
            public void run() {
                Toast.makeText(TestActivity.this, "Maaf, waktu pengerjaan anda telah melebihi 10 menit.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, 600000);
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
        String url = GlobalValue.serverURL+"testSoal/" + id;
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
        tipe = getIntent().getStringExtra("tipe");
        if (tipe.equalsIgnoreCase("pretest")) {
            test_title.setText("Pre-test");
        } else {
            test_title.setText("Post-test");
        }
        id = getIntent().getIntExtra("id", -1);
    }

    @Override
    public void OnRadioClicked(int position, String jawaban) {
        listSoal.get(position).setJawaban(jawaban);
    }

    @Override
    public void OnSelesai() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TestActivity.this);
        int progressHistoryId = sharedPreferences.getInt(GlobalValue.progressHistoryId, -1);

        String url = GlobalValue.serverURL+"createProgress";
        RequestQueue myQueue = Volley.newRequestQueue(TestActivity.this);

        JSONObject parameter = new JSONObject();
        try {
            parameter.put("id_progress_histories", progressHistoryId);
            parameter.put("id_pelatihan", id);
            parameter.put("status", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String url = GlobalValue.serverURL+"insertTestJawaban";
                        RequestQueue myQueue = Volley.newRequestQueue(TestActivity.this);

                        JSONArray parameter1 = new JSONArray();
                        for (int i = 0; i< listSoal.size(); i++){
                            JSONObject temp1 = new JSONObject();
                            try {
                                temp1.put("id_progress", response.getInt("id"));
                                temp1.put("id_test_soal", listSoal.get(i).getId());
                                temp1.put("jawaban", listSoal.get(i).getJawaban());
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
                                        Intent returnIntent = new Intent();
                                        setResult(Activity.RESULT_OK, returnIntent);
                                        finish();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                        String body = "";
                                        //get status code here
                                        //get response body and parse with appropriate encoding
                                        if(error.networkResponse.data!=null) {
                                            body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                                        }
                                        //do stuff with the body...
                                        Log.e("ERROR API", body);
                                    }
                                }
                        );

                        myQueue.add(request);
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

    @Override
    public void onStop () {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }
}
