package com.uc.appsinovatifguru;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

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
        datasaya_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCurrUser.setName(datasaya_username.getEditText().getText().toString().trim());
                dataCurrUser.setUsia(Integer.parseInt(datasaya_usia.getEditText().getText().toString().trim()));
                dataCurrUser.setJenis_kelamin(datasaya_jeniskelamin.getEditText().getText().toString().trim());
                dataCurrUser.setStatus_pernikahan(datasaya_statuspernikahan.getEditText().getText().toString().trim());
                dataCurrUser.setJumlah_anak(Integer.parseInt(datasaya_jumlahanak.getEditText().getText().toString().trim()));
                dataCurrUser.setAsal_sekolah(datasaya_asalsekolah.getEditText().getText().toString().trim());
                dataCurrUser.setLama_mengajar(Integer.parseInt(datasaya_lamamengajar.getEditText().getText().toString().trim()));
                dataCurrUser.setJenjang_mengajar(datasaya_jenjangmengajar.getEditText().getText().toString().trim());
                dataCurrUser.setMata_pelajaran(datasaya_mata_pelajaran.getEditText().getText().toString().trim());
                dataCurrUser.setPendidikan(datasaya_pendidikan.getEditText().getText().toString().trim());
                if(datasaya_isIlmuPendidikan.getEditText().getText().toString().trim().equalsIgnoreCase("Ilmu Kependidikan (FKIP/PGSD/PGTK)")){
                    dataCurrUser.setIlmuPendidikan(true);
                }else if(datasaya_isIlmuPendidikan.getEditText().getText().toString().trim().equalsIgnoreCase("Non Kependidikan")) {
                    dataCurrUser.setIlmuPendidikan(false);
                }
                saveData();
            }
        });
    }

    private void saveData(){
        String url = GlobalValue.serverURL+"updateUser";
        RequestQueue myQueue = Volley.newRequestQueue(getContext());

        JSONObject parameter = new JSONObject();
        try {
            parameter.put("id", currUser.getUid());
            parameter.put("name", dataCurrUser.getName());
            parameter.put("usia", dataCurrUser.getUsia());
            parameter.put("jenis_kelamin", dataCurrUser.getJenis_kelamin());
            parameter.put("status_pernikahan", dataCurrUser.getStatus_pernikahan());
            parameter.put("jumlah_anak", dataCurrUser.getJumlah_anak());
            parameter.put("asal_sekolah", dataCurrUser.getAsal_sekolah());
            parameter.put("lama_mengajar", dataCurrUser.getLama_mengajar());
            parameter.put("jenjang_mengajar", dataCurrUser.getJenjang_mengajar());
            parameter.put("mata_pelajaran", dataCurrUser.getMata_pelajaran());
            parameter.put("pendidikan", dataCurrUser.getPendidikan());
            parameter.put("isIlmuPendidikan", dataCurrUser.isIlmuPendidikan());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equalsIgnoreCase("sukses")){
                                parent.simpanClicked();
                                Toast.makeText(getContext(), "Sukses menyimpan", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Gagal menyimpan", Toast.LENGTH_LONG).show();
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

    private void showData(){
        if(!dataCurrUser.getName().isEmpty()){
            datasaya_username.getEditText().setText(dataCurrUser.getName());
        }

        datasaya_usia.getEditText().setText(String.valueOf(dataCurrUser.getUsia()));

        if(!dataCurrUser.getJenis_kelamin().equalsIgnoreCase("null")){
            datasaya_jeniskelamin.getEditText().setText(dataCurrUser.getJenis_kelamin());
        }
        if(!dataCurrUser.getStatus_pernikahan().equalsIgnoreCase("null")){
            datasaya_statuspernikahan.getEditText().setText(dataCurrUser.getStatus_pernikahan());
        }
        if(!dataCurrUser.getAsal_sekolah().equalsIgnoreCase("null")){
            datasaya_asalsekolah.getEditText().setText(dataCurrUser.getAsal_sekolah());
        }

        datasaya_lamamengajar.getEditText().setText(String.valueOf(dataCurrUser.getLama_mengajar()));

        if(!dataCurrUser.getJenjang_mengajar().equalsIgnoreCase("null")){
            datasaya_jenjangmengajar.getEditText().setText(dataCurrUser.getJenjang_mengajar());
        }

        if(dataCurrUser.getMata_pelajaran().equalsIgnoreCase("null")){
            datasaya_mata_pelajaran.getEditText().setText("");
        }else{
            datasaya_mata_pelajaran.getEditText().setText(dataCurrUser.getMata_pelajaran());
        }

        datasaya_jumlahanak.getEditText().setText(String.valueOf(dataCurrUser.getJumlah_anak()));

        if(!dataCurrUser.getPendidikan().equalsIgnoreCase("null")){
            datasaya_pendidikan.getEditText().setText(dataCurrUser.getPendidikan());
        }
        if(dataCurrUser.isIlmuPendidikan()){
            datasaya_isIlmuPendidikan.getEditText().setText("Ilmu Kependidikan (FKIP/PGSD/PGTK)");
        }else{
            datasaya_isIlmuPendidikan.getEditText().setText("Non Kependidikan");
        }

        initSpinner();
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