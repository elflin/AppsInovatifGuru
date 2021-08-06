package com.uc.appsinovatifguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView main_botnavview;
    private FrameLayout main_framelayout;
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setBottomNavBar();
    }

    private void setBottomNavBar() {
        this.main_botnavview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (item.getItemId() == R.id.menu_home)
                    selectedFragment = new HomeFragment();
                else if (item.getItemId() == R.id.menu_result)
                    selectedFragment = new ResultFragment();
                else if (item.getItemId() == R.id.menu_profile) {
                    selectedFragment = new ProfileFragment();
                    if(currUser == null) {
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    }
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_framelayout, selectedFragment).commit();
                return true;
            }
        });

    }

    private void initView() {
        main_botnavview = findViewById(R.id.main_botnavview);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_framelayout, new HomeFragment()).commit();
        mAuth = FirebaseAuth.getInstance();
        currUser = mAuth.getCurrentUser();
    }


}