package com.uc.appsinovatifguru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SurveymenuActivity extends AppCompatActivity {

    private androidx.appcompat.widget.AppCompatButton
            surveymenu_menu1,
            surveymenu_menu2,
            surveymenu_menu3,
            surveymenu_menu4,
            surveymenu_menu5,
            surveymenu_menu6,
            surveymenu_menu7,
            surveymenu_reset;

    private ImageButton surveymenu_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveymenu);
        initView();
        setListener();
    }

    private void setListener() {
        surveymenu_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Perilaku Inovartif Guru");
                startActivity(intent);
            }
        });

        surveymenu_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Intensi Berinovasi");
                startActivity(intent);
            }
        });

        surveymenu_menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Sikap Terhadap Inovasi");
                startActivity(intent);
            }
        });

        surveymenu_menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Norma Subyektif terhadap Kreativitas");
                startActivity(intent);
            }
        });

        surveymenu_menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Efikasi Berinovasi");
                startActivity(intent);
            }
        });

        surveymenu_menu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Budaya Organisasi Berorientasi Pembelajaran");
                startActivity(intent);
            }
        });

        surveymenu_menu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetailsurveyActivity.class);
                intent.putExtra("variabel", "Self-Determination");
                startActivity(intent);
            }
        });

        surveymenu_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        surveymenu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        surveymenu_menu1 = findViewById(R.id.surveymenu_menu1);
        surveymenu_menu2 = findViewById(R.id.surveymenu_menu2);
        surveymenu_menu3 = findViewById(R.id.surveymenu_menu3);
        surveymenu_menu4 = findViewById(R.id.surveymenu_menu4);
        surveymenu_menu5 = findViewById(R.id.surveymenu_menu5);
        surveymenu_menu6 = findViewById(R.id.surveymenu_menu6);
        surveymenu_menu7 = findViewById(R.id.surveymenu_menu7);
        surveymenu_reset = findViewById(R.id.surveymenu_reset);
        surveymenu_back = findViewById(R.id.surveymenu_back);
    }
}