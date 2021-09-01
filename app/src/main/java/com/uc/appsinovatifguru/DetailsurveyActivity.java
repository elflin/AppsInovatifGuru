package com.uc.appsinovatifguru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailsurveyActivity extends AppCompatActivity {

    private ImageButton surveymenu_back;
    private Button surveymenu_start;
    private RecyclerView surveymenu_recyclerview;
    private TextView surveymenu_jurnal, surveymenu_title;
    private Intent intent;
    private String variabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsurvey);
        initView();

    }

    private void initView() {
        surveymenu_back = findViewById(R.id.surveymenu_back);
        surveymenu_start = findViewById(R.id.surveymenu_start);
        surveymenu_recyclerview = findViewById(R.id.surveymenu_recyclerview);
        surveymenu_jurnal = findViewById(R.id.surveymenu_jurnal);
        surveymenu_title = findViewById(R.id.surveymenu_title);
        intent = getIntent();
        variabel = intent.getStringExtra("variabel");
        if(variabel.equalsIgnoreCase("Perilaku Inovartif Guru")){
            surveymenu_jurnal.setText("Janssen, O. (2000). Job demands, perceptions of effort-reward fairness and innovative work behavior. Journal of Occupational and Organizational Psychology, 287–302.");
        } else if(variabel.equalsIgnoreCase("Intensi Berinovasi")){
            surveymenu_jurnal.setText("Jin, N. C. (2004). Individual and contextual predictors of creative performance: The mediating role of psychological processes. Creativity Research Journal, 16(2–3), 187–199. https://doi.org/10.1207/s15326934crj1602&3_4\n\nLi, G., Wang, X., & Wu, J. (2019). How scientific researchers form green innovation behavior: An empirical analysis of China’s enterprises. Technology in Society, 56(June), 134–146. https://doi.org/10.1016/j.techsoc.2018.09.012");
        } else if(variabel.equalsIgnoreCase("Sikap Terhadap Inovasi")){
            surveymenu_jurnal.setText("Chapman, G., & Hewitt-Dundas, N. (2018). The effect of public support on senior manager attitudes to innovation. Technovation, 69(September), 28–39. https://doi.org/10.1016/j.technovation.2017.10.004");
        } else if(variabel.equalsIgnoreCase("Norma Subyektif terhadap Kreativitas")){
            surveymenu_jurnal.setText("Carmeli, A., & Schaubroeck, J. (2007). The influence of leaders’ and other referents’ normative expectations on individual involvement in creative work. Leadership Quarterly, 18(1), 35–48. https://doi.org/10.1016/j.leaqua.2006.11.001");
        } else if(variabel.equalsIgnoreCase("Efikasi Berinovasi")){
            surveymenu_jurnal.setText("Brockhus, S., Kolk, T. E. C. van der, Koeman, B., & Badke-Schaub, P. G. (2014). The influence of creative self-efficacy on creative performance. Proceeding of International Design Conference DESIGN 2014, 437–444.");
        } else if(variabel.equalsIgnoreCase("Budaya Organisasi Berorientasi Pembelajaran")){
            surveymenu_jurnal.setText("Calantone, R. J., Cavusgil, S. T., & Zhao, Y. (2002). Learning orientation , firm innovation capability , and firm performance. Industrial Marketing Management, 31, 515–524.");
        } else if(variabel.equalsIgnoreCase("Self-Determination")){
            surveymenu_jurnal.setText("Fernet, C., Sencal, C., Guay, F., Marsh, H., & Dowson, M. (2008). The Work Tasks Motivation Scale for Teachers (WTMST). Journal of Career Assessment, 16(2), 256–279. https://doi.org/10.1177/1069072707305764");
        }
    }
}