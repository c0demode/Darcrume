package com.walderman.darcrume;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class Controller_Develop extends AppCompatActivity {
    private static long startTimeInMilliseconds = 30000;

    private DatabaseHelper db;
    private EditText editText_Minutes;
    private EditText editText_Seconds;
    private TextView textViewCountDown;
    private Button btnSetTimer;
    private Button btnStartPause;
    private Button btnReset;
    private CountDownTimer countDownTimer;
    private Boolean timerIsRunning = false;
    private long timeRemainingInMilliseconds = startTimeInMilliseconds;
    private MediaPlayer sound;// = MediaPlayer.create(Controller_Develop.this, R.raw.accomplished);
    private ArrayList<Film> filmList = new ArrayList<>();
    private FilmArrayAdapter filmArrayAdapter;
    private Spinner filmSpinner;
    private Spinner chem1Spinner;
    private Spinner chem2Spinner;
    private Spinner chem3Spinner;
    private Film selectedFilm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop);

        db = new DatabaseHelper(getApplicationContext());

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
                if (timerIsRunning) {
                    pauseTimer();
                } else {
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

        filmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedFilm = (Film) adapterView.getItemAtPosition(i);
                activateChem1Spinner(selectedFilm);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    /**
     * This method is called during onCreate. It finds views for all variables that needs views.
     */
    private void findViewsForVariables() {
        sound = MediaPlayer.create(Controller_Develop.this, R.raw.accomplished);

        //Relating to Film spinner
        filmList = db.getAllFilms();
        filmArrayAdapter = new FilmArrayAdapter(this, filmList);
        filmSpinner = findViewById(R.id.spinner_SelectFilm);
        filmSpinner.setAdapter(filmArrayAdapter);

        //Relating to Chem 1 spinner


        //Relating to the Timer
        editText_Minutes = findViewById(R.id.editText_Min);
        editText_Seconds = findViewById(R.id.editText_Sec);
        btnSetTimer = findViewById(R.id.btn_setTimer);
        textViewCountDown = findViewById(R.id.textView_Countdown);
        btnStartPause = findViewById(R.id.btn_start_pause);
        btnReset = findViewById(R.id.btn_reset);
    }

    /**
     * Take user's selection from 2 spinners, assign to variable and set timer
     *
     * @return
     */
    private int setTimer() {
        int timeMin = 60000 * Integer.parseInt(editText_Minutes.getText().toString());
        int timeSec = 1000 * Integer.parseInt(editText_Seconds.getText().toString());
        int timeToSet = timeMin + timeSec;
        String formattedText = formatMillisecondsToMinutesSecond(timeToSet);
        textViewCountDown.setText(formattedText);
        timeRemainingInMilliseconds = timeToSet;
        return timeToSet;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(setTimer(), 1000) {
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
     *
     * @param milliseconds
     * @return
     */
    private String formatMillisecondsToMinutesSecond(int milliseconds) {
        int minutes = (milliseconds / 1000) / 60;
        int seconds = (milliseconds / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
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

    private void activateChem1Spinner(Film selectedFilm) {
        if (selectedFilm.getType().equals("BW")){
            //populate spinner w/ bw dev
        }else{
            //populate spinner w/ color dev
        }

        //create onitemselectedlistener to activate next spinner, repeat process
    }
}

