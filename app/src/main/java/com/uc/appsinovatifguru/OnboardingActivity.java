package com.uc.appsinovatifguru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uc.appsinovatifguru.Adapter.OnboardingAdapter;
import com.uc.appsinovatifguru.Model.OnBoardingItem;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicator;
    private Button buttonOnboardingAction;
    private ViewPager2 onboardingViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        initView();
        setOnboardingItem();
        setOnboadingIndicator();
        setCurrentOnboardingIndicators(0);
        setListener();
    }

    private void setListener() {
        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                } else {
                    SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                    sharedPreferencesEditor.putBoolean(GlobalValue.Onboarding_Complete, true);
                    sharedPreferencesEditor.apply();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void initView() {
        layoutOnboardingIndicator = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnBoardingAction);
        onboardingViewPager = findViewById(R.id.onboardingViewPager);
    }

    private void setOnboadingIndicator() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                30, 30
        );
        layoutParams.setMargins(10, 0, 10, 3);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicator.addView(indicators[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicators(int index) {
        int childCount = layoutOnboardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicator.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            }
        }
        if (index == onboardingAdapter.getItemCount() - 1){
            buttonOnboardingAction.setText("Get Started");
        }else {
            buttonOnboardingAction.setText("Next");
        }
    }
    private void setOnboardingItem() {
        List<OnBoardingItem> onBoardingItems = new ArrayList<>();

        OnBoardingItem itemFastFood = new OnBoardingItem();
        itemFastFood.setTitle("Choose your meal");
        itemFastFood.setDescription("You can easily choose your meal and take it!");
        itemFastFood.setImage(R.drawable.onboarding_img);

        OnBoardingItem itemPayOnline = new OnBoardingItem();
        itemPayOnline.setTitle("Choose your payment");
        itemPayOnline.setDescription("You can pay us using any methods, online or offline!");
        itemPayOnline.setImage(R.drawable.onboarding_img);

        OnBoardingItem itemEatTogether = new OnBoardingItem();
        itemEatTogether.setTitle("Fast delivery");
        itemEatTogether.setDescription("Our delivery partners are too fast, they will not disappoint you!");
        itemEatTogether.setImage(R.drawable.onboarding_img);

        OnBoardingItem itemDayAndNight = new OnBoardingItem();
        itemDayAndNight.setTitle("Day and Night");
        itemDayAndNight.setDescription("Our service is on day and night!");
        itemDayAndNight.setImage(R.drawable.onboarding_img);

        onBoardingItems.add(itemFastFood);
        onBoardingItems.add(itemPayOnline);
        onBoardingItems.add(itemEatTogether);
        onBoardingItems.add(itemDayAndNight);
        onboardingAdapter = new OnboardingAdapter(onBoardingItems);
        onboardingViewPager.setAdapter(onboardingAdapter);
    }
}