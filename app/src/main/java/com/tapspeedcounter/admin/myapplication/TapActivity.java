package com.tapspeedcounter.admin.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tapspeedcounter.admin.tapspeedcounter.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


/**
 * Created by Anh Hoang on 1/30/2018.
 */

public class TapActivity extends AppCompatActivity {
    private static double tapCount = 0;
    private static double timeElapsed;
    private static double millisDouble;
    private TextView speedTextView;
    private TextView timer;
    private TextView tapPerSecTextView;
    private Button oneFingerTap;
    private Button twoFingerTap;
    private String speedValue;
    private String speedValueToSetText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);
        getSupportActionBar().setTitle("Tap Speed Counter");
        tapCount = 0;

        oneFingerTap = findViewById(R.id.one_finger_tap_button);
        twoFingerTap = findViewById(R.id.two_finger_tap_button);
        speedTextView = findViewById(R.id.tap_speed);
        tapPerSecTextView = findViewById(R.id.tap_per_sec_text_view);
        timer = findViewById(R.id.timer);

        SharedPreferences radioButtonPref = PreferenceManager.getDefaultSharedPreferences(this);
        getTapCheckedOption(radioButtonPref);
        getTimeCheckedOption(radioButtonPref);

        oneFingerTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** If timer get to 1000 stop
                 *  If the countdown prepare hasn't run, don't run the button yet
                 *  stop when it says time's up
                 */
                if (!timer.getText().equals(getString(R.string.times_up))
                        && millisDouble != 1000 && millisDouble != 0) {
                    tapCount++;
                }
            }
        });
        twoFingerTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!timer.getText().equals(getString(R.string.times_up))
                        && millisDouble != 1000 && millisDouble != 0) {
                    tapCount++;
                }
            }
        });

    }

    //Decide what tap button to show
    public void getTapCheckedOption(SharedPreferences sharePref) {
        int checkedId = sharePref.getInt(getString(R.string.tap_checked), 0);
        switch (checkedId) {
            case R.id.one_finger_radio_button:
                twoFingerTap.setVisibility(View.GONE);
                break;
            case R.id.two_finger_radio_button:
                oneFingerTap.setVisibility(View.GONE);
                break;
        }
    }
    //Decide what time to show
    public void getTimeCheckedOption(SharedPreferences sharePref) {
        int checkedId = sharePref.getInt(getString(R.string.time_checked), 0);
        switch (checkedId) {
            case R.id.thirty_second_radio_button:
                int LIMIT_TIME_THIRTY_SECOND = 30000;
                countDownPrepare(mainCounter(LIMIT_TIME_THIRTY_SECOND));
                break;
            case R.id.fifteen_second_radio_button:
                int LIMIT_TIME_FIFTEEN_SECOND = 15000;
                countDownPrepare(mainCounter(LIMIT_TIME_FIFTEEN_SECOND));
                break;
        }
    }

    //Get the speed duh...
    public static double getSpeed() {
        double speed;
        if (timeElapsed < 1) {
            speed = tapCount;
        } else {
            speed = tapCount / (timeElapsed + 1);
        }
        double speedRounded = twoDecimalRounding(speed);
        return speedRounded;
    }

    //Round the speed to 2 decimal places
    public static double twoDecimalRounding(double result) {
        DecimalFormat twoDecimalFormat = new DecimalFormat("00.00", new DecimalFormatSymbols(Locale.US));
        return Double.valueOf(twoDecimalFormat.format(result));
    }

    // This method is used to take the setText to the main thread
    // rather than doing it in the background thread(Timer).
    private void setText(final TextView text,String value) {
        speedValueToSetText = value;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(speedValueToSetText);
            }
        });
    }

    //To prepare user for the click action
    public void countDownPrepare(final BetterCountDownTimer countDownTimer) {
        new BetterCountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                setText(speedTextView, String.valueOf(millisUntilFinished / 1000));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tapPerSecTextView.setVisibility(View.INVISIBLE);
                    }
                });
            }

            public void onFinish() {
                setText(speedTextView, getString(R.string.ready));
                countDownTimer.start();
            }
        }.start();
    }
    //Set color of the timer to RED when done
    public void setColorTimer(final TextView textView,final int color) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setTextColor(color);
            }
        });
    }
    //Set color of the speed based on its magnitude
    public void setColorTapSpeed(final TextView textView,final double speed) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(speed < 1){
                    textView.setTextColor(getResources().getColor(R.color.blue));
                } else if (speed < 2){
                    textView.setTextColor(getResources().getColor(R.color.cyan));
                } else if (speed < 3){
                    textView.setTextColor(getResources().getColor(R.color.teal));
                } else if (speed < 4){
                    textView.setTextColor(getResources().getColor(R.color.green));
                } else if (speed < 5){
                    textView.setTextColor(getResources().getColor(R.color.light_green));
                } else if (speed < 6){
                    textView.setTextColor(getResources().getColor(R.color.lime));
                } else if (speed < 7){
                    textView.setTextColor(getResources().getColor(R.color.yellow));
                } else if (speed < 8){
                    textView.setTextColor(getResources().getColor(R.color.amber));
                } else if (speed < 9){
                    textView.setTextColor(getResources().getColor(R.color.orange));
                } else if (speed < 10){
                    textView.setTextColor(getResources().getColor(R.color.deep_orange));
                } else if (speed < 11){
                    textView.setTextColor(getResources().getColor(R.color.red2));
                } else if (speed < 12){
                    textView.setTextColor(getResources().getColor(R.color.pink));
                }
            }
        });
    }
    //The default countdown timer sucks
    public BetterCountDownTimer mainCounter(final int interval) {
        speedValue = getSpeed()+"";
        return new BetterCountDownTimer(interval, 100, 1000) {

            public void onTick(long millisUntilFinished) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tapPerSecTextView.setVisibility(View.VISIBLE);
                    }
                });
                millisDouble = millisUntilFinished;
                timeElapsed = (interval - millisDouble) / 1000;
                String timeRemain = String.valueOf(millisUntilFinished / 1000) + " sec";
                setText(timer, timeRemain);
                setText(speedTextView, speedValue);
                setColorTapSpeed(speedTextView, getSpeed());
            }

            public void onFinish() {
                setText(speedTextView, speedValue);
                setText(timer, getString(R.string.times_up));
                setColorTimer(timer,Color.RED);

                runOnUiThread(new Runnable() {
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                                startActivity(intent);
                            }
                        }, 3000);
                    }
                });
            }
        };
    }

}
