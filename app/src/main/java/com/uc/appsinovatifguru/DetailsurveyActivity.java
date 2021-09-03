package com.uc.appsinovatifguru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.uc.appsinovatifguru.Adapter.RiwayatAdapter;
import com.uc.appsinovatifguru.Model.Result;
import com.uc.appsinovatifguru.Model.Riwayat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailsurveyActivity extends AppCompatActivity {

    private ImageButton surveymenu_back;
    private Button surveymenu_start;
    private RecyclerView surveymenu_recyclerview;
    private TextView surveymenu_jurnal, surveymenu_title;
    private Intent intent;
    private String variabel;
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;
    private ArrayList<Riwayat> listRiwayat;
    private RiwayatAdapter adapter;
    private Boolean isClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsurvey);
        initView();
        setListener();
    }

    private void setListener() {
        surveymenu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        surveymenu_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), QuizActivity.class);
                intent1.putExtra("variabel", variabel);
                startActivity(intent1);
            }
        });
    }

    private void getDataFromDB() {
        String url = GlobalValue.serverURL+"reportJawaban";
        RequestQueue myQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject parameter = new JSONObject();
        try {
            parameter.put("userId", currUser.getUid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray arrayData = response.getJSONArray("data");
                            for (int i = 0; i < arrayData.length(); i++){
                                JSONObject temp = arrayData.getJSONObject(i);
                                JSONObject variabel1 = temp.getJSONObject("variabel");
                                JSONObject value = temp.getJSONObject("value");
                                JSONObject dimensi = temp.getJSONObject("dimensi");

                                Riwayat riwayat = new Riwayat();
                                if(!temp.getString("updated_at").equalsIgnoreCase("null") && variabel1.getInt(variabel) != 0) {
                                    riwayat.setTanggal(temp.getString("updated_at"));
                                    System.out.println();
                                    riwayat.setRiwayat_nilai(value.getString(variabel));
                                    riwayat.setRiwayat_angka(variabel1.getString(variabel));

                                    if (variabel.equalsIgnoreCase("Perilaku Inovatif Guru")) {
                                        riwayat.setDimensi1("Idea Generation");
                                        riwayat.setNilai1(dimensi.getString("Idea Generation"));
                                        riwayat.setDimensi2("Idea Promotion");
                                        riwayat.setNilai2(dimensi.getString("Idea Promotion"));
                                        riwayat.setDimensi3("Idea Realization");
                                        riwayat.setNilai3(dimensi.getString("Idea Realization"));
                                    } else if (variabel.equalsIgnoreCase("Intensi Berinovasi")) {
                                        riwayat.setDimensi1("Intensi Berinovasi");
                                        riwayat.setNilai1(dimensi.getString("Intensi Berinovasi"));
                                    } else if (variabel.equalsIgnoreCase("Sikap Terhadap Inovasi")) {
                                        riwayat.setDimensi1("Support For Innovation");
                                        riwayat.setNilai1(dimensi.getString("Support For Innovation"));
                                        riwayat.setDimensi2("Risk Of Tolerance");
                                        riwayat.setNilai2(dimensi.getString("Risk Of Tolerance"));
                                        riwayat.setDimensi3("Openness To External Knowledge");
                                        riwayat.setNilai3(dimensi.getString("Openness To External Knowledge"));
                                    } else if (variabel.equalsIgnoreCase("Norma Subyektif terhadap Kreativitas")) {
                                        riwayat.setDimensi1("Family Expectations For Creativity");
                                        riwayat.setNilai1(dimensi.getString("Family Expectations For Creativity"));
                                        riwayat.setDimensi2("Leader Expectations For Creativity");
                                        riwayat.setNilai2(dimensi.getString("Leader Expectations For Creativity"));
                                        riwayat.setDimensi3("Customer Expectations For Creativity");
                                        riwayat.setNilai3(dimensi.getString("Customer Expectations For Creativity"));
                                    } else if (variabel.equalsIgnoreCase("Efikasi Berinovasi")) {
                                        riwayat.setDimensi1("Personal Assumptions/beliefs About Own Creativity");
                                        riwayat.setNilai1(dimensi.getString("Personal Assumptions/beliefs About Own Creativity"));
                                        riwayat.setDimensi2("Evidence-based Assessment Of Creative Self-efficacy");
                                        riwayat.setNilai2(dimensi.getString("Evidence-based Assessment Of Creative Self-efficacy"));
                                    } else if (variabel.equalsIgnoreCase("Budaya Organisasi Berorientasi Pembelajaran")) {
                                        riwayat.setDimensi1("Commitment To Learning");
                                        riwayat.setNilai1(dimensi.getString("Commitment To Learning"));
                                        riwayat.setDimensi2("Shared Vision");
                                        riwayat.setNilai2(dimensi.getString("Shared Vision"));
                                        riwayat.setDimensi3("Open-mindedness");
                                        riwayat.setNilai3(dimensi.getString("Open-mindedness"));
                                        riwayat.setDimensi4("Intraorganizational Knowledge Sharing");
                                        riwayat.setNilai4(dimensi.getString("Intraorganizational Knowledge Sharing"));
                                    } else if (variabel.equalsIgnoreCase("Self-Determination")) {
                                        riwayat.setDimensi1("Intrinsic Motivation");
                                        riwayat.setNilai1(dimensi.getString("Intrinsic Motivation"));
                                        riwayat.setDimensi2("Identified Regulation");
                                        riwayat.setNilai2(dimensi.getString("Identified Regulation"));
                                    }

                                    listRiwayat.add(riwayat);
                                }
                            }
                            adapter.notifyDataSetChanged();
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
        surveymenu_back = findViewById(R.id.surveymenu_back);
        surveymenu_start = findViewById(R.id.surveymenu_start);
        surveymenu_recyclerview = findViewById(R.id.surveymenu_recyclerview);
        surveymenu_jurnal = findViewById(R.id.surveymenu_jurnal);
        surveymenu_title = findViewById(R.id.surveymenu_title);
        intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        listRiwayat = new ArrayList<>();
        adapter = new RiwayatAdapter(listRiwayat);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        surveymenu_recyclerview.setLayoutManager(manager);
        surveymenu_recyclerview.setAdapter(adapter);
        currUser = mAuth.getCurrentUser();
        variabel = intent.getStringExtra("variabel");
        isClicked = intent.getBooleanExtra("isClicked", false);
        if (isClicked){
            surveymenu_start.setEnabled(false);
        }else{
            surveymenu_start.setEnabled(true);
        }
        if(variabel.equalsIgnoreCase("Perilaku Inovatif Guru")){
            surveymenu_jurnal.setText("Janssen, O. (2000). Job demands, perceptions of effort-reward fairness and innovative work behavior. Journal of Occupational and Organizational Psychology, 287–302.");
        } else if(variabel.equalsIgnoreCase("Intensi Berinovasi")){
            surveymenu_jurnal.setText("Jin, N. C. (2004). Individual and contextual predictors of creative performance: The mediating role of psychological processes. Creativity Research Journal, 16(2–3), 187–199. https://doi.org/10.1207/s15326934crj1602&3_4\n\nLi, G., Wang, X., & Wu, J. (2019). How scientific researchers form green innovation behavior: An empirical analysis of China’s enterprises. Technology in Society, 56(June), 134–146. https://doi.org/10.1016/j.techsoc.2018.09.012");
        } else if(variabel.equalsIgnoreCase("Sikap Terhadap Inovasi")){
            surveymenu_jurnal.setText("Chapman, G., & Hewitt-Dundas, N. (2018). The effect of public support on senior manager attitudes to innovation. Technovation, 69(September), 28–39. https://doi.org/10.1016/j.technovation.2017.10.004");
        } else if(variabel.equalsIgnoreCase("Norma Subyektif terhadap Kreativitas")){
            surveymenu_jurnal.setText("Carmeli, A., & Schaubroeck, J. (2007). The influence of leaders’ and other referents’ normative expectations on individual involvement in creative work. Leadership Quarterly, 18(1), 35–48. https://doi.org/10.1016/j.leaqua.2006.11.001");
        } else if(variabel.equalsIgnoreCase("Efikasi Berinovasi")){
            surveymenu_jurnal.setText("Brockhus, S., Kolk, T. E. C. van der, Koeman, B., & Badke-Schaub, P. G. (2014). The influence of creative self-efficacy on creative performance. Proceeding of International Design Conference DESIGN 2014, 437–444.");
        } else if(variabel.equalsIgnoreCase("Budaya Organisasi Berorientasi Pembelajaran")){
            surveymenu_jurnal.setText("Calantone, R. J., Cavusgil, S. T., & Zhao, Y. (2002). Learning orientation , firm innovation capability , and firm performance. Industrial Marketing Management, 31, 515–524.");
        } else if(variabel.equalsIgnoreCase("Self-Determination")){
            surveymenu_jurnal.setText("Fernet, C., Sencal, C., Guay, F., Marsh, H., & Dowson, M. (2008). The Work Tasks Motivation Scale for Teachers (WTMST). Journal of Career Assessment, 16(2), 256–279. https://doi.org/10.1177/1069072707305764");
        }
        getDataFromDB();
    }
}