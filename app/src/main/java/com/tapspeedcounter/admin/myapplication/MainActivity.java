package com.tapspeedcounter.admin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tapspeedcounter.admin.tapspeedcounter.R;


public class MainActivity extends AppCompatActivity {
    public RadioButton oneFingerButton, twoFingerButton, thirtySecondButton, fifteenSecondButton;
    public RadioGroup tapRadioGroup, timeRadioGroup;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-7448187601886870/3074318079");

        getSupportActionBar().setTitle("Tap Speed Counter");
        oneFingerButton = findViewById(R.id.one_finger_radio_button);
        oneFingerButton.setChecked(true);
        twoFingerButton = findViewById(R.id.two_finger_radio_button);
        fifteenSecondButton = findViewById(R.id.fifteen_second_radio_button);
        thirtySecondButton = findViewById(R.id.thirty_second_radio_button);
        fifteenSecondButton.setChecked(true);
        tapRadioGroup = findViewById(R.id.finger_radio_group);
        timeRadioGroup = findViewById(R.id.time_radio_group);
        Button doneButton = findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDoneButton();
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    public void setDoneButton() {
        SharedPreferences radioButtonPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = radioButtonPref.edit();
        editor.putInt(getString(R.string.tap_checked), tapRadioGroup.getCheckedRadioButtonId());
        editor.putInt(getString(R.string.time_checked), timeRadioGroup.getCheckedRadioButtonId());
        editor.apply();
        Intent intent = new Intent(this, TapActivity.class);
        startActivity(intent);
    }
}
