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
    private ArrayList<Chem> chem1List = new ArrayList<>();
    private ArrayList<Chem> chem2List = new ArrayList<>();
    private ArrayList<Chem> chem3List = new ArrayList<>();
    private ArrayList<Film> filmList = new ArrayList<>();
    private ArrayList<Chem> chemList = new ArrayList<>();
    private ChemArrayAdapter chemArrayAdapter1;
    private ChemArrayAdapter chemArrayAdapter2;
    private ChemArrayAdapter chemArrayAdapter3;
    private FilmArrayAdapter filmArrayAdapter;
    private Spinner filmSpinner;
    private Spinner chem1Spinner;
    private Spinner chem2Spinner;
    private Spinner chem3Spinner;
    private Film selectedFilm;
    private Chem selectedChem1;
    private Chem selectedChem2;
    private Chem selectedChem3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop);

        db = new DatabaseHelper(getApplicationContext());

        configureVariables();
        setOnClickListeners();
    }


    /**
     * This method is called during onCreate. It finds views for all variables that needs views.
     */
    private void configureVariables() {
        sound = MediaPlayer.create(Controller_Develop.this, R.raw.accomplished);

        //Relating to Film spinner
        filmList = db.getAllFilms();
        filmArrayAdapter = new FilmArrayAdapter(this, filmList);
        filmSpinner = findViewById(R.id.spinner_SelectFilm);
        filmSpinner.setAdapter(filmArrayAdapter);

        //Relating to Chem spinners
        chemList = db.getAllChems();
        chem1Spinner = findViewById(R.id.spinner_Chem1);
        chemArrayAdapter1 = new ChemArrayAdapter(this, chemList);
        chem1Spinner.setAdapter(chemArrayAdapter1);

        chem2Spinner = findViewById(R.id.spinner_Chem2);
        chemArrayAdapter2 = new ChemArrayAdapter(this, chem2List);
        chem2Spinner.setAdapter(chemArrayAdapter2);

        chem3Spinner = findViewById(R.id.spinner_Chem3);
        chemArrayAdapter3 = new ChemArrayAdapter(this, chem3List);
        chem3Spinner.setAdapter(chemArrayAdapter3);

        //Relating to the Timer
        editText_Minutes = findViewById(R.id.editText_Min);
        editText_Seconds = findViewById(R.id.editText_Sec);
        btnSetTimer = findViewById(R.id.btn_setTimer);
        textViewCountDown = findViewById(R.id.textView_Countdown);
        btnStartPause = findViewById(R.id.btn_start_pause);
        btnReset = findViewById(R.id.btn_reset);
    }

    private void setOnClickListeners(){
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
                getChemsForSelectedFilm(selectedFilm);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getChemsForSelectedFilm(Film selectedFilm){
        if(selectedFilm.getType().toUpperCase().equals("BW")){
            chem1List = db.getAllChemsOfRoleType(DatabaseHelper.ChemRole.BWDEV);
            chem1Spinner.setAdapter(chemArrayAdapter1);
            chemArrayAdapter1.notifyDataSetChanged();

            chem2List = db.getAllChemsOfRoleType(DatabaseHelper.ChemRole.BWSTP);
            chemArrayAdapter2.notifyDataSetChanged();

            chem3List = db.getAllChemsOfRoleType(DatabaseHelper.ChemRole.BWFIX);
            chemArrayAdapter3.notifyDataSetChanged();
        }
        if(selectedFilm.getType().toUpperCase().equals("COLOR")){
            chem1List = db.getAllChemsOfRoleType(DatabaseHelper.ChemRole.CLRDEV);
            chemArrayAdapter1.notifyDataSetChanged();

            chem2List = db.getAllChemsOfRoleType(DatabaseHelper.ChemRole.CLRBLX);
            chemArrayAdapter2.notifyDataSetChanged();

            chem3List = db.getAllChemsOfRoleType(DatabaseHelper.ChemRole.CLRSTB);
            chemArrayAdapter3.notifyDataSetChanged();
        }
    }

    /** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** **
     * .___________. __  .___  ___.  _______ .______       **
     * |           ||  | |   \/   | |   ____||   _  \      **
     * `---|  |----`|  | |  \  /  | |  |__   |  |_)  |     **
     *     |  |     |  | |  |\/|  | |   __|  |      /      **
     *     |  |     |  | |  |  |  | |  |____ |  |\  \----. **
     *     |__|     |__| |__|  |__| |_______|| _| `._____| **
     ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** ** **
     */

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
     * Take an int representing milliseconds and break apart into 2 ints representing minutes and seconds. Return a string representing MM:SS format.
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
     * Pass the timeRemainingInMilliseconds to a method and update as MM:SS format string. Use this new string to update the countdown timer.
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
}

