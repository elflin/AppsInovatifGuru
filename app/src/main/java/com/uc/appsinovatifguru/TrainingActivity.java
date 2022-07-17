package com.uc.appsinovatifguru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.uc.appsinovatifguru.Adapter.TrainingRecyclerViewAdapter;
import com.uc.appsinovatifguru.Model.Training;

import java.util.ArrayList;

public class TrainingActivity extends AppCompatActivity {
    private ImageButton training_back;
    private RecyclerView training_recyclerview;
    private TrainingRecyclerViewAdapter trainingAdapter;
    private ArrayList<Training> listTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        initView();
        setListener();
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
        listTraining = new ArrayList<>();
        addDummyData();
        
        trainingAdapter = new TrainingRecyclerViewAdapter(listTraining, getLifecycle());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        training_recyclerview.setLayoutManager(manager);
        training_recyclerview.setAdapter(trainingAdapter);
    }

    private void addDummyData() {
        Training perkenalan = new Training();
        perkenalan.setJudul("Perkenalan");
        perkenalan.setProfilPeneliti("profil");

        Training consent = new Training();
        consent.setJudul("Informed Consent");
        consent.setTemplateWord("template word");

        Training preTest = new Training();
        preTest.setJudul("Pre-test Learning");
        preTest.setPreTest(true);

        Training pertemuan1 = new Training();
        pertemuan1.setJudul("Pertemuan 1: Pengantar");
        pertemuan1.setDownloadPdf("p1 down");
        pertemuan1.setVideo("https://www.youtube.com/embed/lYPe4MsALk4");

        Training pertemuan2 = new Training();
        pertemuan2.setJudul("Pertemuan 2: Membangun Sikap & Efikasi Berinovasi");
        pertemuan2.setDownloadPdf("p2 down");
        pertemuan2.setVideo("https://www.youtube.com/embed/Jb88eui8SqQ");
        pertemuan2.setUploadPdf("p2 upload pdf");

        Training pertemuan3 = new Training();
        pertemuan3.setJudul("Pertemuan 3: Memperkuat Intensi Berinovasi");
        pertemuan3.setDownloadPdf("p3 down");
        pertemuan3.setVideo("https://www.youtube.com/embed/WfVF-Ec4naQ");
        pertemuan3.setUploadPdf("p3 upload pdf");

        Training postTest = new Training();
        postTest.setJudul("Post-test Learning");
        postTest.setPostTest(true);

        Training eval = new Training();
        eval.setJudul("Evaluasi");
        eval.setEval(true);

        listTraining.add(perkenalan);
        listTraining.add(consent);
        listTraining.add(preTest);
        listTraining.add(pertemuan1);
        listTraining.add(pertemuan2);
        listTraining.add(pertemuan3);
        listTraining.add(postTest);
        listTraining.add(eval);
    }
}
