package com.uc.appsinovatifguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView main_botnavview;
    private FrameLayout main_framelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setBottomNavBar();
    }

    private void setBottomNavBar() {
        this.main_botnavview.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        if (item.getItemId() == R.id.menu_home)
                            selectedFragment = new HomeFragment();
                        else if (item.getItemId() == R.id.menu_result)
                            selectedFragment = new ResultFragment();
                        else if (item.getItemId() == R.id.menu_profile)
                            selectedFragment = new ProfileFragment();

                        getSupportFragmentManager().beginTransaction().replace(R.id.main_framelayout, selectedFragment).commit();

                        return true;
                    }
                });
    }

    private void initView() {
        main_botnavview = findViewById(R.id.main_botnavview);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_framelayout, new HomeFragment()).commit();
    }


}