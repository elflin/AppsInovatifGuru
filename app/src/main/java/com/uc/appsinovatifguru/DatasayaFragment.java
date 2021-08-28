package com.uc.appsinovatifguru;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

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

public class DatasayaFragment extends Fragment {

    private View view;
    private ProfileActivity parent;
    private User dataCurrUser;
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;
    private TextInputLayout datasaya_username, datasaya_usia, datasaya_jeniskelamin,
            datasaya_statuspernikahan, datasaya_jumlahanak, datasaya_asalsekolah, datasaya_lamamengajar,
            datasaya_jenjangmengajar, datasaya_mata_pelajaran, datasaya_pendidikan, datasaya_isIlmuPendidikan;
    private Button datasaya_simpan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_datasaya, container, false);
        initView();
        postData();
        setListener();
        return view;
    }

    private void setListener() {
    }

    private void showData(){

    }

    private void initView() {
        parent = (ProfileActivity) getActivity();
        datasaya_username = view.findViewById(R.id.datasaya_username);
        datasaya_usia = view.findViewById(R.id.datasaya_usia);
        datasaya_jeniskelamin = view.findViewById(R.id.datasaya_jeniskelamin);
        datasaya_statuspernikahan = view.findViewById(R.id.datasaya_statuspernikahan);
        datasaya_asalsekolah = view.findViewById(R.id.datasaya_asalsekolah);
        datasaya_lamamengajar = view.findViewById(R.id.datasaya_lamamengajar);
        datasaya_jenjangmengajar = view.findViewById(R.id.datasaya_jenjangmengajar);
        datasaya_mata_pelajaran = view.findViewById(R.id.datasaya_mata_pelajaran);
        datasaya_jumlahanak = view.findViewById(R.id.datasaya_jumlahanak);
        datasaya_pendidikan = view.findViewById(R.id.datasaya_pendidikan);
        datasaya_isIlmuPendidikan = view.findViewById(R.id.datasaya_isIlmuPendidikan);
        datasaya_simpan = view.findViewById(R.id.datasaya_simpan);
        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
        dataCurrUser = new User();
        initSpinner();
    }

    private void initSpinner(){
        String [] jenis_kelamin = getResources().getStringArray(R.array.jenis_kelamin);
        String [] status_pernikahan = getResources().getStringArray(R.array.status_pernikahan);
        String [] asal_sekolah = getResources().getStringArray(R.array.asal_sekolah);
        String [] jenjang_mengajar = getResources().getStringArray(R.array.jenjang_mengajar);
        String [] pendidikan = getResources().getStringArray(R.array.pendidikan);
        String [] isIlmuPendidikan = getResources().getStringArray(R.array.isIlmuPendidikan);

        ArrayAdapter jenisKelamin_adapter = new ArrayAdapter(getContext(), R.layout.drop_down_layout, jenis_kelamin);
        ArrayAdapter status_pernikahan_adapter = new ArrayAdapter(getContext(), R.layout.drop_down_layout, status_pernikahan);
        ArrayAdapter asal_sekolah_adapter = new ArrayAdapter(getContext(), R.layout.drop_down_layout, asal_sekolah);
        ArrayAdapter jenjang_mengajar_adapter = new ArrayAdapter(getContext(), R.layout.drop_down_layout, jenjang_mengajar);
        ArrayAdapter pendidikan_adapter = new ArrayAdapter(getContext(), R.layout.drop_down_layout, pendidikan);
        ArrayAdapter isIlmuPendidikan_adapter = new ArrayAdapter(getContext(), R.layout.drop_down_layout, isIlmuPendidikan);

        AutoCompleteTextView jenisKelamin_act = (AutoCompleteTextView) datasaya_jeniskelamin.getEditText();
        jenisKelamin_act.setAdapter(jenisKelamin_adapter);

        AutoCompleteTextView status_pernikahan_act = (AutoCompleteTextView) datasaya_statuspernikahan.getEditText();
        status_pernikahan_act.setAdapter(status_pernikahan_adapter);

        AutoCompleteTextView asal_sekolah_act = (AutoCompleteTextView) datasaya_asalsekolah.getEditText();
        asal_sekolah_act.setAdapter(asal_sekolah_adapter);

        AutoCompleteTextView jenjang_mengajar_act = (AutoCompleteTextView) datasaya_jenjangmengajar.getEditText();
        jenjang_mengajar_act.setAdapter(jenjang_mengajar_adapter);

        AutoCompleteTextView pendidikan_act = (AutoCompleteTextView) datasaya_pendidikan.getEditText();
        pendidikan_act.setAdapter(pendidikan_adapter);

        AutoCompleteTextView isIlmuPendidikan_act = (AutoCompleteTextView) datasaya_isIlmuPendidikan.getEditText();
        isIlmuPendidikan_act.setAdapter(isIlmuPendidikan_adapter);
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