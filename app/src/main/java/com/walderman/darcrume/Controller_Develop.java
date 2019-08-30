package com.walderman.darcrume;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Controller_Develop extends AppCompatActivity {
    private static long startTimeInMilliseconds = 0;

    private Spinner spinnerMinutes;
    private Spinner spinnerSeconds;
    private TextView textViewCountDown;
    private Button btnSetTimer;
    private Button btnStartPause;
    private Button btnReset;
    private CountDownTimer countDownTimer;
    private Boolean timerIsRunning = false;
    private long timeRemainingInMilliseconds = startTimeInMilliseconds;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        findViewsForVariables();

        btnSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimer();
            }
        });
        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerIsRunning){
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateCountDownText();
    }

    private void setTimer() {
        int spinMin = 60000 * Integer.parseInt(spinnerMinutes.getSelectedItem().toString());
        int spinSec = 1000 *Integer.parseInt(spinnerSeconds.getSelectedItem().toString());
        int timeToSet = spinMin + spinSec;
        timeRemainingInMilliseconds =timeToSet;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeRemainingInMilliseconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemainingInMilliseconds = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerIsRunning = false;
                btnStartPause.setText("Start");
                btnStartPause.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.VISIBLE);
            }
        }.start();

        timerIsRunning = true;
        btnStartPause.setText("pause");
        btnReset.setVisibility(View.INVISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (timeRemainingInMilliseconds / 1000) / 60;
        int seconds = (int) (timeRemainingInMilliseconds / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeLeftFormatted);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerIsRunning = false;
        btnStartPause.setText("Start");
        btnReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        timeRemainingInMilliseconds = startTimeInMilliseconds;
        updateCountDownText();
        btnReset.setVisibility(View.INVISIBLE);
        btnStartPause.setVisibility(View.VISIBLE);
    }

    private void findViewsForVariables() {
        spinnerMinutes = findViewById(R.id.spinner_Minutes);
        spinnerSeconds = findViewById(R.id.spinner_Seconds);
        btnSetTimer = findViewById(R.id.btn_setTimer);

        textViewCountDown = findViewById(R.id.textView_Countdown);
        btnStartPause = findViewById(R.id.btn_start_pause);
        btnReset = findViewById(R.id.btn_reset);
    }
}
