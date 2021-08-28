package com.uc.appsinovatifguru;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uc.appsinovatifguru.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailakunFragment extends Fragment {

    private View view;
    private ProfileActivity parent;
    private ImageView detailakun_pp;
    private Button detailakun_simpan;
    private ImageButton detailakun_editpp;
    private TextInputLayout detailakun_email, detailakun_oldpass, detailakun_newpass, detailakun_confirmpass;
    private User dataCurrUser;
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detailakun, container, false);
        initView();
        postData();
        setListener();
        return view;
    }

    private void setListener() {
    }

    private void showData() {
        detailakun_email.getEditText().setText(dataCurrUser.getEmail());
    }

    private void initView() {
        parent = (ProfileActivity) getActivity();
        detailakun_pp = view.findViewById(R.id.detailakun_pp);
        detailakun_simpan = view.findViewById(R.id.detailakun_simpan);
        detailakun_editpp = view.findViewById(R.id.detailakun_editpp);
        detailakun_email = view.findViewById(R.id.detailakun_email);
        detailakun_oldpass = view.findViewById(R.id.detailakun_oldpass);
        detailakun_newpass = view.findViewById(R.id.detailakun_newpass);
        detailakun_confirmpass = view.findViewById(R.id.detailakun_confirmpass);
        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
        dataCurrUser = new User();
    }

    private void postData(){
        String url = GlobalValue.serverURL+"getUser";
        RequestQueue myQueue = Volley.newRequestQueue(getContext());

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

                            showData();
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