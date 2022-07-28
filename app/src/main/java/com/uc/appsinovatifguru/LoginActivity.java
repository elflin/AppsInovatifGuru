package com.uc.appsinovatifguru;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uc.appsinovatifguru.Model.AESCrypt;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputLayout login_email, login_password;
    private Button login_loginButton, login_registerButton;
    private ActivityResultLauncher<Intent> mStartForResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponent();
        setIntentResult();
        setListener();
    }

    private void setIntentResult() {
        mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            if (intent.getBooleanExtra("isRegistered", false) == true) {
                                gotoMainClass();
                            }
                        }
                    }
                });
    }

    private void setListener() {
        login_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getEditText().getText().toString().toLowerCase().trim();
                String rawPass = login_password.getEditText().getText().toString().trim();
                if(validate(email, rawPass)) {
                    String password = "";
                    try {
                        password = AESCrypt.encrypt(rawPass);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("IsiEmail", email);
                    Log.d("RawPassword", rawPass);
                    Log.d("IsiPassword", password);
                    mAuth.signInWithEmailAndPassword(email, rawPass)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("1", "signInWithEmail:success");
                                        gotoMainClass();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("1", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        login_registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartForResult.launch(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });
    }

    private boolean validate(String email, String rawPass) {
        boolean cek = true;
        if (email.isEmpty()){
            cek = false;
            login_email.setError("Tolong lengkapi email");
        }else{
            login_email.setError("");
        }
        if (rawPass.isEmpty()){
            cek = false;
            login_password.setError("Tolong lengkapi password");
        }else{
            login_password.setError("");
        }
        return cek;
    }

    private void gotoMainClass(){
        Toast.makeText(LoginActivity.this, "Authentication Success.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void initComponent() {
        mAuth = FirebaseAuth.getInstance();
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_loginButton = findViewById(R.id.login_loginButton);
        login_registerButton = findViewById(R.id.login_registerButton);
    }


}