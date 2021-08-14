package com.uc.appsinovatifguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.uc.appsinovatifguru.Model.AESCrypt;

public class RegisterActivity extends AppCompatActivity {

    private Button register_registerbutton;
    private TextInputLayout register_username, register_email, register_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponent();
        setListener();
    }

    private void setListener() {
        register_registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = register_email.getEditText().getText().toString().toLowerCase().trim();
                String rawPass = register_password.getEditText().getText().toString().toLowerCase().trim();
                String password = "";
                try {
                    password = AESCrypt.encrypt(rawPass);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent();
                                    intent.putExtra("isRegistered", true);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getBaseContext(),"Registrasi gagal", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    private void initComponent() {
        register_registerbutton = findViewById(R.id.register_registerbutton);
        register_username = findViewById(R.id.register_username);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        mAuth = FirebaseAuth.getInstance();
    }
}