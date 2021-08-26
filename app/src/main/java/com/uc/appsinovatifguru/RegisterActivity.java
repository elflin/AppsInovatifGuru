package com.uc.appsinovatifguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uc.appsinovatifguru.Model.AESCrypt;
import com.uc.appsinovatifguru.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button register_registerbutton, register_kembali;
    private TextInputLayout register_username, register_email, register_password;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponent();
        setListener();
    }

    private void setListener() {
        register_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register_registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = register_username.getEditText().getText().toString().toLowerCase().trim();
                String email = register_email.getEditText().getText().toString().toLowerCase().trim();
                String rawPass = register_password.getEditText().getText().toString().toLowerCase().trim();
                String password = "";
                try {
                    password = AESCrypt.encrypt(rawPass);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String finalPassword = password;
                mAuth.createUserWithEmailAndPassword(email, finalPassword)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    user = mAuth.getCurrentUser();
                                    User temp = new User(user.getUid(), name, email, rawPass);
                                    postData(temp);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getBaseContext(),"Registrasi gagal", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    private void postData(User temp){
        String url = GlobalValue.serverURL+"createUser";
        RequestQueue myQueue = Volley.newRequestQueue(this);

        JSONObject parameter = new JSONObject();
        try {
            parameter.put("id", temp.getId());
            parameter.put("name", temp.getName());
            parameter.put("email", temp.getEmail());
            parameter.put("password", temp.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
            deleteUserWhenFailed();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equalsIgnoreCase("sukses")) {
                                Intent intent = new Intent();
                                intent.putExtra("isRegistered", true);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }else{
                                deleteUserWhenFailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            deleteUserWhenFailed();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        deleteUserWhenFailed();
                    }
                }
        );

        myQueue.add(request);
    }

    private void deleteUserWhenFailed(){
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getBaseContext(),"Registrasi gagal", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initComponent() {
        register_registerbutton = findViewById(R.id.register_registerbutton);
        register_username = findViewById(R.id.register_username);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_kembali = findViewById(R.id.register_kembali);
        mAuth = FirebaseAuth.getInstance();
    }
}