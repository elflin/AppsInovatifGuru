package com.uc.appsinovatifguru;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.util.IOUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.GsonBuilder;
import com.uc.appsinovatifguru.Adapter.TrainingRecyclerViewAdapter;
import com.uc.appsinovatifguru.Helpers.FileUploadService;
import com.uc.appsinovatifguru.Helpers.ServiceGenerator;
import com.uc.appsinovatifguru.Model.FileUpload;
import com.uc.appsinovatifguru.Model.Training;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

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
        checkProgress();
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

        Training yelyel = new Training();
        yelyel.setJudul("Yel-Yel");
        yelyel.setType("materi");
        yelyel.setLink("https://youtu.be/fS6EW1v314Q");

        listTraining.add(perkenalan);
        listTraining.add(consent);
        listTraining.add(yelyel);

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
                                temp3.setLink_ppt(temp2.getString("link_ppt"));
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

                                        if (listTraining.get(i).getJudul().equals("Evaluasi Pelatihan")) {
                                            checkNilaiEval(temp2.getInt("id"));
                                        }
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

        if (requestCode >= 2) {
            int id_pelatihan = requestCode - 1;
            if(resultCode == Activity.RESULT_OK){
                FileUploadService service =
                        ServiceGenerator.createService(FileUploadService.class);
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                byte[] file = null;
                try {
                    file = IOUtils.toByteArray(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (file.length > 4096000) {
                    Toast.makeText(this, "Ukuran file terlalu besar (Max: 4 MB)", Toast.LENGTH_SHORT).show();
                    return;
                }

                String encodedFile = Base64.encodeToString(file, Base64.DEFAULT);

                service.uploadFile(encodedFile, getFileName(data.getData())).enqueue(new Callback<FileUpload>() {
                    @Override
                    public void onResponse(Call<FileUpload> call, retrofit2.Response<FileUpload> response) {
                        if (response.code() == 200) {
                            Toast.makeText(TrainingActivity.this, "Upload file successful", Toast.LENGTH_SHORT).show();
                            checkProgress();
                            boolean progressExists = false;
                            for (Training training : listTraining) {
                                if (training.getId() == id_pelatihan && training.getAttempts() != 0) {
                                    progressExists = true;
                                    break;
                                }
                            }
                            if (progressExists) {
                                updateProgressUploadFIle(response.body().getLink_path(), id_pelatihan);
                            } else {
                                createProgressUploadFIle(response.body().getLink_path(), id_pelatihan);
                            }
                        } else {
                            Log.d("APRKAWP", response.errorBody().toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<FileUpload> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }
    }

    private void checkNilaiEval(int id) {
        String url = GlobalValue.serverURL+"resultEvaluasiJawaban";
        RequestQueue myQueue = Volley.newRequestQueue(this);

        JSONObject parameter = new JSONObject();
        try {
            parameter.put("id_progress", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (Training training : listTraining) {
                                if (training.getJudul().equals("Evaluasi Pelatihan")) {
                                    training.setResult(response.getInt("result"));
                                    break;
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

    private void createProgressUploadFIle(String link_path, int id_pelatihan) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TrainingActivity.this);
        int progressHistoryId = sharedPreferences.getInt(GlobalValue.progressHistoryId, -1);

        String url = GlobalValue.serverURL+"createProgress";
        RequestQueue myQueue = Volley.newRequestQueue(TrainingActivity.this);

        JSONObject parameter = new JSONObject();
        try {
            parameter.put("id_progress_histories", progressHistoryId);
            parameter.put("id_pelatihan", id_pelatihan);
            parameter.put("status", true);
            parameter.put("path_submission", link_path);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        checkProgress();
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

    private void updateProgressUploadFIle(String link_path, int id_pelatihan) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TrainingActivity.this);
        int progressHistoryId = sharedPreferences.getInt(GlobalValue.progressHistoryId, -1);

        String url = GlobalValue.serverURL+"updateProgress";
        RequestQueue myQueue = Volley.newRequestQueue(TrainingActivity.this);

        JSONObject parameter = new JSONObject();
        try {
            parameter.put("id_progress_histories", progressHistoryId);
            parameter.put("id_pelatihan", id_pelatihan);
            parameter.put("status", true);
            parameter.put("path_submission", link_path);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        checkProgress();
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

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        result = result.substring(result.lastIndexOf(".") + 1);
        return result;
    }
}
