package com.uc.appsinovatifguru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private Button register_registerbutton;
    private TextInputLayout register_username, register_email, register_password;

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
                Intent intent = new Intent();
                intent.putExtra("isRegistered", true);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initComponent() {
        register_registerbutton = findViewById(R.id.register_registerbutton);
        register_username = findViewById(R.id.register_username);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
    }
}