package com.uc.appsinovatifguru;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uc.appsinovatifguru.Adapter.ResultRecyclerViewAdapter;
import com.uc.appsinovatifguru.Model.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultFragment extends Fragment {

    private View view;
    private RecyclerView result_recyclerview;
    private ArrayList<Result> listResult;
    private ResultRecyclerViewAdapter resultRecyclerViewAdapter;
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_result, container, false);
        initView();
        getDataFromDB();
        return view;
    }

    private void getDataFromDB() {
        String url = GlobalValue.serverURL+"reportJawaban";
        RequestQueue myQueue = Volley.newRequestQueue(getContext());

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
                                JSONObject variabel = temp.getJSONObject("variabel");
                                JSONObject value = temp.getJSONObject("value");

                                Result temp1 = new Result();
                                if(!temp.getString("updated_at").equalsIgnoreCase("null")) {
                                    temp1.setTanggal(temp.getString("updated_at"));
                                    temp1.setNilai1(String.valueOf(variabel.getInt("Perilaku Inovatif Guru")) + " (" + value.getString("Perilaku Inovatif Guru") + ")");
                                    temp1.setNilai2(String.valueOf(variabel.getInt("Intensi Berinovasi")) + " (" + value.getString("Intensi Berinovasi") + ")");
                                    temp1.setNilai3(String.valueOf(variabel.getInt("Sikap Terhadap Inovasi")) + " (" + value.getString("Sikap Terhadap Inovasi") + ")");
                                    temp1.setNilai4(String.valueOf(variabel.getInt("Norma Subyektif terhadap Kreativitas")) + " (" + value.getString("Norma Subyektif terhadap Kreativitas") + ")");
                                    temp1.setNilai5(String.valueOf(variabel.getInt("Efikasi Berinovasi")) + " (" + value.getString("Efikasi Berinovasi") + ")");
                                    temp1.setNilai6(String.valueOf(variabel.getInt("Budaya Organisasi Berorientasi Pembelajaran")) + " (" + value.getString("Budaya Organisasi Berorientasi Pembelajaran") + ")");
                                    temp1.setNilai7(String.valueOf(variabel.getInt("Self-Determination")) + " (" + value.getString("Self-Determination") + ")");

                                    listResult.add(temp1);
                                }
                            }
                            resultRecyclerViewAdapter.notifyDataSetChanged();
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
        result_recyclerview = view.findViewById(R.id.result_recyclerview);
        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        listResult = new ArrayList<>();
        resultRecyclerViewAdapter = new ResultRecyclerViewAdapter(listResult);
        result_recyclerview.setLayoutManager(manager);
        result_recyclerview.setAdapter(resultRecyclerViewAdapter);
    }
}