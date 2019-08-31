package com.walderman.darcrume;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Controller_Develop extends AppCompatActivity {
    private static long startTimeInMilliseconds = 0;

    private EditText editText_Minutes;
    private EditText editText_Seconds;
    private TextView textViewCountDown;
    private Button btnSetTimer;
    private Button btnStartPause;
    private Button btnReset;
    private CountDownTimer countDownTimer;
    private Boolean timerIsRunning = false;
    private long timeRemainingInMilliseconds = startTimeInMilliseconds;
    private MediaPlayer sound = MediaPlayer.create(Controller_Develop.this, R.raw.accomplished);


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop);

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

    /**
     * Take user's selection from 2 spinners, assign to variable and set timer
     * @return
     */
    private int setTimer() {
        int timeMin = 60000 * Integer.parseInt(editText_Minutes.getText().toString());
        int timeSec = 1000 *Integer.parseInt(editText_Seconds.getText().toString());
        int timeToSet = timeMin + timeSec;
        String formattedText = formatMillisecondsToMinutesSecond(timeToSet);
        textViewCountDown.setText(formattedText);
        timeRemainingInMilliseconds =timeToSet;
        return timeToSet;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(setTimer(),1000) {
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

    /**
     * Take an int representing milliseconds and break apart into 2 ints representing minutes and seconds.
     * Return a string representing MM:SS format.
     * @param milliseconds
     * @return
     */
    private String formatMillisecondsToMinutesSecond(int milliseconds){
        int minutes = (milliseconds / 1000) / 60;
        int seconds = (milliseconds / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        return timeLeftFormatted;
    }

    /**
     * Pass the timeRemainingInMilliseconds to a method and update as MM:SS format string.
     * Use this new string to update the countdown timer.
     */
    private void updateCountDownText() {
        String updatedCountDownText = formatMillisecondsToMinutesSecond((int) timeRemainingInMilliseconds);
        textViewCountDown.setText(updatedCountDownText);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerIsRunning = false;
        btnStartPause.setText("Start");
        btnReset.setVisibility(View.VISIBLE);
        sound.start();
    }

    private void resetTimer() {
        timeRemainingInMilliseconds = setTimer();
        updateCountDownText();
        btnReset.setVisibility(View.INVISIBLE);
        btnStartPause.setVisibility(View.VISIBLE);
    }

    private void findViewsForVariables() {
        editText_Minutes = findViewById(R.id.editText_Min);
        editText_Seconds = findViewById(R.id.editText_Sec);
        btnSetTimer = findViewById(R.id.btn_setTimer);

        textViewCountDown = findViewById(R.id.textView_Countdown);
        btnStartPause = findViewById(R.id.btn_start_pause);
        btnReset = findViewById(R.id.btn_reset);
    }
}
