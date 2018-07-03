package com.tapspeedcounter.admin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.tapspeedcounter.admin.tapspeedcounter.R;

/**
 * Created by ADMIN on 1/30/2018.
 */

public class ResultActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setTitle("Tap Speed Counter");
        String textResult = TapActivity.getSpeed() +"";
        TextView yourTapSpeedView = findViewById(R.id.your_tap_speed);
        yourTapSpeedView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_1));
        TextView resultTextView = findViewById(R.id.result_text_view);
        resultTextView.setText(textResult);
        resultTextView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_2));
        Button recalculateButton = findViewById(R.id.recalculate_button);
        recalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(getApplicationContext(), TapActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
        recalculateButton.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_1));

        Button otherOptionsButton = findViewById(R.id.other_options_button);
        otherOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }

            }
        });
        otherOptionsButton.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_1));
        mInterstitialAd = new InterstitialAd(this);

        mInterstitialAd.setAdUnitId("ca-app-pub-7448187601886870/9421111253");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}

