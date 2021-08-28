package com.uc.appsinovatifguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uc.appsinovatifguru.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView main_botnavview;
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;
    private User dataCurrUser;
    private TextView main_nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setBottomNavBar();
        postData();
    }

    public void refreshData(){
        postData();
    }

    private void setBottomNavBar() {
        this.main_botnavview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (item.getItemId() == R.id.menu_home)
                    selectedFragment = new HomeFragment();
                else if (item.getItemId() == R.id.menu_result)
                    selectedFragment = new ResultFragment();
                else if (item.getItemId() == R.id.menu_profile) {
                    selectedFragment = new ProfileFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_framelayout, selectedFragment).commit();
                return true;
            }
        });

    }

    private void initView() {
        main_botnavview = findViewById(R.id.main_botnavview);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_framelayout, new HomeFragment()).commit();
        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
        dataCurrUser = new User();
        main_nama = findViewById(R.id.main_nama);
    }

    private void postData(){
        String url = GlobalValue.serverURL+"getUser";
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
//                            Log.d("uidFirebase", currUser.getUid());
                            JSONObject jsonObject = response.getJSONArray("data").getJSONObject(0);
//                            Log.d("JsonCheck", jsonObject.getString("id"));
                            dataCurrUser.setId(jsonObject.getString("id"));
                            dataCurrUser.setName(jsonObject.getString("name"));
                            dataCurrUser.setEmail(jsonObject.getString("email"));
                            dataCurrUser.setJenis_kelamin(jsonObject.getString("jenis_kelamin"));
                            dataCurrUser.setStatus_pernikahan(jsonObject.getString("status_pernikahan"));
                            dataCurrUser.setAsal_sekolah(jsonObject.getString("asal_sekolah"));
                            dataCurrUser.setJenjang_mengajar(jsonObject.getString("jenjang_mengajar"));
                            dataCurrUser.setMata_pelajaran(jsonObject.getString("mata_pelajaran"));
                            dataCurrUser.setPendidikan(jsonObject.getString("pendidikan"));
                            dataCurrUser.setLama_mengajar(jsonObject.getInt("lama_mengajar"));
                            dataCurrUser.setJumlah_anak(jsonObject.getInt("jumlah_anak"));
                            dataCurrUser.setUsia(jsonObject.getInt("usia"));
                            if(jsonObject.getInt("isIlmuPendidikan")==1) {
                                dataCurrUser.setIlmuPendidikan(true);
                            }else{
                                dataCurrUser.setIlmuPendidikan(false);
                            }
                            main_nama.setText(dataCurrUser.getName().substring(0, dataCurrUser.getName().indexOf(" ")));
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