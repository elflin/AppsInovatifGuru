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
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.uc.appsinovatifguru.Adapter.EvalRecyclerViewAdapter;
import com.uc.appsinovatifguru.Adapter.SoalRecyclerViewAdapter;
import com.uc.appsinovatifguru.Listener.EvalListener;
import com.uc.appsinovatifguru.Listener.SoalListener;
import com.uc.appsinovatifguru.Model.Eval;
import com.uc.appsinovatifguru.Model.Soal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EvalActivity extends AppCompatActivity implements EvalListener {

    private TextView eval_title;
    private ImageButton eval_back;
    private RecyclerView eval_recyclerview;
    private EvalRecyclerViewAdapter evalAdapter;
    private ArrayList<Eval> listPertanyaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval);
        initView();
        fillPertanyaan();
        setListener();
    }

    private void setListener() {
        eval_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fillPertanyaan() {
        listPertanyaan.add(new Eval("Pelatihan ini bermanfaat bagi profesi saya sebagai guru."));
        listPertanyaan.add(new Eval("Materi pelatihan ini disampaikan dengan jelas."));
        listPertanyaan.add(new Eval("Aplikasi komputer dalam pelatihan ini mudah digunakan."));
        listPertanyaan.add(new Eval("Tugas atau aktivitas dalam pelatihan ini menarik untuk dilakukan."));
        listPertanyaan.add(new Eval("Waktu yang diberikan dalam pelatihan ini sesuai dengan jadwal saya."));
        listPertanyaan.add(new Eval("Secara keseluruhan saya puas dengan penyelenggaraan pelatihan ini."));
        listPertanyaan.add(new Eval("Saya berharap pelatihan semacam ini akan diselenggarakan lagi."));
    }

    private void initView() {
        eval_title = findViewById(R.id. eval_title);
        eval_back = findViewById(R.id. eval_back);
        eval_recyclerview = findViewById(R.id. eval_recyclerview);
        listPertanyaan = new ArrayList<>();
        evalAdapter = new EvalRecyclerViewAdapter(listPertanyaan, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        eval_recyclerview.setLayoutManager(manager);
        eval_recyclerview.setAdapter(evalAdapter);
        eval_title.setText("Evaluasi");
    }

    @Override
    public void OnRadioClicked(int position, int nilai) {
        String jawaban  = "";
        switch (nilai) {
            case 1:
                jawaban = "Sangat setuju";
                break;
            case 2:
                jawaban = "Setuju";
                break;
            case 3:
                jawaban = "Cukup";
                break;
            case 4:
                jawaban = "Tidak setuju";
                break;
            case 5:
                jawaban = "Sangat tidak setuju";
                break;
        }
        listPertanyaan.get(position).setJawaban(jawaban);
    }

    @Override
    public void OnSelesai(String pesan_kesan) {
        String url = GlobalValue.serverURL+"insertEvaluasiJawaban";
        RequestQueue myQueue = Volley.newRequestQueue(this);

        JSONObject parameter = new JSONObject();
        try {
            parameter.put("id_progress", 1);
            for (int i = 0; i< listPertanyaan.size(); i++){
                parameter.put("jawaban" + (i + 1), listPertanyaan.get(i).getJawaban());
            }
            parameter.put("pesan_kesan", pesan_kesan);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("200")){
                                SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                                sharedPreferencesEditor.putBoolean(GlobalValue.eval, true);

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