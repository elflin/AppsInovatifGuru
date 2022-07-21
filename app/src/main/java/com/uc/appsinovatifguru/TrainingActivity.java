package com.uc.appsinovatifguru;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
import com.uc.appsinovatifguru.Adapter.TrainingRecyclerViewAdapter;
import com.uc.appsinovatifguru.Model.Training;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrainingActivity extends AppCompatActivity {
    private ImageButton training_back;
    private RecyclerView training_recyclerview;
    private TrainingRecyclerViewAdapter trainingAdapter;
    private ArrayList<Training> listTraining;
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        getAllPelatihans();
        initView();
        createProgressHistory();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkProgress();
    }

    private void setListener() {
        training_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        training_back = findViewById(R.id. training_back);
        training_recyclerview = findViewById(R.id. training_recyclerview);
        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();

        trainingAdapter = new TrainingRecyclerViewAdapter(listTraining, getLifecycle());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        training_recyclerview.setLayoutManager(manager);
        training_recyclerview.setAdapter(trainingAdapter);
    }

    public void getAllPelatihans() {
        listTraining = new ArrayList<>();

        Training perkenalan = new Training();
        perkenalan.setJudul("Perkenalan");
        perkenalan.setType("perkenalan");

        Training consent = new Training();
        consent.setJudul("Informed Consent");
        consent.setType("consent");

        listTraining.add(perkenalan);
        listTraining.add(consent);

        String url = GlobalValue.serverURL+"pelatihans";
        RequestQueue myQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray temp1 = response.getJSONArray("data");

                            for (int i = 0; i < temp1.length(); i++) {
                                JSONObject temp2 = temp1.getJSONObject(i);
                                Training temp3 = new Training();
                                temp3.setId(temp2.getInt("id"));
                                temp3.setJudul(temp2.getString("judul"));
                                temp3.setDeskripsi(temp2.getString("deskripsi"));
                                temp3.setLink(temp2.getString("link"));
                                temp3.setType(temp2.getString("type"));
//                                temp3.setStatus(temp2.getInt("status"));
                                listTraining.add(temp3);
                            }
                            trainingAdapter.notifyDataSetChanged();
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

    public void createProgressHistory() {
        String url = GlobalValue.serverURL+"getLastProgressHistory";
        RequestQueue myQueue = Volley.newRequestQueue(this);

        JSONObject parameter = new JSONObject();
        try {
            parameter.put("uid", currUser.getUid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        if (!sharedPreferences.contains(GlobalValue.progressHistoryId)) {
                            try {
                                SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                sharedPreferencesEditor.putInt(GlobalValue.progressHistoryId, response.getJSONObject("data").getInt("id"));
                                sharedPreferencesEditor.apply();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        String url = GlobalValue.serverURL+"createProgressHistory";
                        RequestQueue myQueue = Volley.newRequestQueue(TrainingActivity.this);

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
                                                sharedPreferencesEditor.putInt(GlobalValue.progressHistoryId, response.getInt("historyId"));
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
                }
        );

        myQueue.add(request);
    }

    private void checkProgress() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TrainingActivity.this);
        int progressHistoryId = sharedPreferences.getInt(GlobalValue.progressHistoryId, -1);
        String url = GlobalValue.serverURL+"showProgress";
        RequestQueue myQueue = Volley.newRequestQueue(this);

        JSONObject parameter = new JSONObject();
        try {
            parameter.put("id_progress_histories", progressHistoryId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray temp1 = response.getJSONArray("data");

                            for (int i = 0; i < listTraining.size(); i++) {
                                listTraining.get(i).setAttempts(0);
                                for (int j = 0; j < temp1.length(); j++) {
                                    JSONObject temp2 = temp1.getJSONObject(j);
                                    if (listTraining.get(i).getId() == temp2.getInt("id_pelatihan")) {
                                        listTraining.get(i).setAttempts(listTraining.get(i).getAttempts() + 1);
                                    }
                                }
                            }
                            trainingAdapter.notifyDataSetChanged();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                checkProgress();
                trainingAdapter.notifyDataSetChanged();
            }
        }
    }
}
