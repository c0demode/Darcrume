package com.walderman.darcrume;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class DevelopActivity extends AppCompatActivity implements NoteDialog.NoteDialogListener {

    private static long startTimeInMilliseconds = 0;
    private DatabaseHelper db;
    private TextView tvChem1;
    private TextView tvChem2;
    private TextView tvChem3;
    private EditText editText_DevMinutes;
    private EditText editText_DevSeconds;
    public TextView textViewCountDown;
    private Button btnSetTimer;
    private Button btnStartStop;
    private Button btnReset;
    private Button btnDevLogSession;
    private CountDownTimer developTimer;
    private CountDownTimer intervalTimer;
    private Boolean timerIsRunning = false;
    private long timeRemainingInMilliseconds = startTimeInMilliseconds;
    private MediaPlayer sound;// = MediaPlayer.create(DevelopActivity.this, R.raw.accomplished);
    private ArrayList<Chem> chem1List = new ArrayList<>();
    private ArrayList<Chem> chem2List = new ArrayList<>();
    private ArrayList<Chem> chem3List = new ArrayList<>();
    private ArrayList<Film> filmList = new ArrayList<>();
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
    private RadioButton radDevBW;
    private RadioButton radDevCol;
    private int timeToSet;
    private String sessionNote;//All Variables



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop);

        db = new DatabaseHelper(getApplicationContext());

        configureVariables();
        setOnClickListeners();

        populateSpinners(DatabaseHelper.FilmType.BW);
    }

    /**
     * This method is called during onCreate. It finds views for all variables that needs views.
     */
    private void configureVariables() {
        sound = MediaPlayer.create(DevelopActivity.this, R.raw.accomplished);

        tvChem1 = findViewById(R.id.textView_SelectChem1);
        tvChem2 = findViewById(R.id.textView_SelectChem2);
        tvChem3 = findViewById(R.id.textView_SelectChem3);

        radDevBW = findViewById(R.id.radDevBW);
        radDevBW.setChecked(true);
        radDevCol = findViewById(R.id.radDevCol);

        filmSpinner = findViewById(R.id.spinner_SelectFilm);
        chem1Spinner = findViewById(R.id.spinner_Chem1);
        chem2Spinner = findViewById(R.id.spinner_Chem2);
        chem3Spinner = findViewById(R.id.spinner_Chem3);

        //Relating to the Timer
        editText_DevMinutes = findViewById(R.id.editText_DevMin);
        editText_DevSeconds = findViewById(R.id.editText_DevSec);

        btnSetTimer = findViewById(R.id.btn_setMainTimer);
        textViewCountDown = findViewById(R.id.textView_Countdown);
        btnStartStop = findViewById(R.id.btn_start_pause);
        btnReset = findViewById(R.id.btn_reset);

        btnDevLogSession = findViewById(R.id.btnDevLogSession);

    }

    private void setOnClickListeners(){
        radDevBW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateSpinners(DatabaseHelper.FilmType.BW);
            }
        });

        radDevCol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateSpinners(DatabaseHelper.FilmType.COLOR);
            }
        });

        btnSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(editText_DevMinutes.getText().toString()) + Integer.parseInt(editText_DevSeconds.getText().toString()) > 0)
                    setTimer();
                else
                    Toast.makeText(getApplicationContext(), "Please enter a time", Toast.LENGTH_SHORT).show();
            }
        });

        btnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerIsRunning) {
                    stopTimer();
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnDevLogSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNoteDialog();
            }
        });
    }

    private void populateSpinners(DatabaseHelper.FilmType filmType) {

        //Initialize variables
        DatabaseHelper.ChemRole chem1 = null;
        DatabaseHelper.ChemRole chem2 = null;
        DatabaseHelper.ChemRole chem3 = null;
        //Define variables according to filmType selected
        if (filmType.equals(DatabaseHelper.FilmType.BW)){
            chem1 = DatabaseHelper.ChemRole.BWDEV;
            chem2 = DatabaseHelper.ChemRole.BWSTP;
            chem3 = DatabaseHelper.ChemRole.BWFIX;
            tvChem1.setText("Developer:");
            tvChem2.setText("Stop Bath:");
            tvChem3.setText("Fixer");
        } else if (filmType.equals(DatabaseHelper.FilmType.COLOR)){
            chem1 = DatabaseHelper.ChemRole.CLRDEV;
            chem2 = DatabaseHelper.ChemRole.CLRBLX;
            chem3 = DatabaseHelper.ChemRole.CLRSTB;
            tvChem1.setText("Developer:");
            tvChem2.setText("Blix:");
            tvChem3.setText("Stabilizer:");
        }

        //create lists of film and chems depending on filmType selected
        filmList = db.getAllFilmsByType(filmType);
        chem1List = db.getAllChemsOfRoleType(chem1);
        chem2List = db.getAllChemsOfRoleType(chem2);
        chem3List = db.getAllChemsOfRoleType(chem3);

        filmArrayAdapter = new FilmArrayAdapter(this, filmList);
        filmSpinner.setAdapter(filmArrayAdapter);

        chemArrayAdapter1 = new ChemArrayAdapter(this, chem1List);
        chem1Spinner.setAdapter(chemArrayAdapter1);

        chemArrayAdapter2 = new ChemArrayAdapter(this, chem2List);
        chem2Spinner.setAdapter(chemArrayAdapter2);

        chemArrayAdapter3 = new ChemArrayAdapter(this, chem3List);
        chem3Spinner.setAdapter(chemArrayAdapter3);

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

    private void alertToAgitate() {
        Toast.makeText(this, "Time to agitate chemistry", Toast.LENGTH_SHORT).show();
    }

    /**
     * Take an int representing milliseconds and break apart into 2 ints representing minutes and seconds. Return a string representing MM:SS format.
     * @param milliseconds
     * @return
     */
    public String formatMillisecondsToMinutesSecond(int milliseconds) {
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

    public int setTimer() {
        int timeMin = 60000 * Integer.parseInt(editText_DevMinutes.getText().toString());
        int timeSec = 1000 * Integer.parseInt(editText_DevSeconds.getText().toString());
        timeToSet = timeMin + timeSec;

        //pass timeToSet to a method that will divide out minutes and seconds, and put a colon between them,
        String formattedTime = formatMillisecondsToMinutesSecond(timeToSet);
        textViewCountDown.setText(formattedTime);
        timeRemainingInMilliseconds = timeToSet;
        return timeToSet;
    }

    private void startTimer() {

        developTimer = new CountDownTimer(timeToSet, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemainingInMilliseconds = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerIsRunning = false;
                btnStartStop.setText("Start");
                btnStartStop.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.VISIBLE);
            }
        }.start();

        intervalTimer = new CountDownTimer(timeToSet, 30000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemainingInMilliseconds = millisUntilFinished;
                if ( timeToSet - millisUntilFinished > 25000 ) {
                    sound.start();
                    alertToAgitate();
                }
            }

            @Override
            public void onFinish() {
                timerIsRunning = false;
                Toast.makeText(DevelopActivity.this, "Pour out chemistry and follow next step!", Toast.LENGTH_LONG).show();
            }
        }.start();

        timerIsRunning = true;
        btnStartStop.setText("Stop");
        btnReset.setVisibility(View.INVISIBLE);
    }

    private void stopTimer() {
        developTimer.cancel();
        intervalTimer.cancel();
        btnReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        timeRemainingInMilliseconds = setTimer();
        updateCountDownText();
        btnReset.setVisibility(View.INVISIBLE);
        btnStartStop.setVisibility(View.VISIBLE);

        btnStartStop.setText("Start");

        timerIsRunning = false;
    }

    private void openNoteDialog() {
        NoteDialog noteDialog = new NoteDialog();
        noteDialog.show(getSupportFragmentManager(),"Note Dialog");
    }

    @Override
    public void applyText(String note) {
        sessionNote = note;
        //we have to wait until we get back a sessionNote before we call saveSession()
        saveSession();
    }


    private void saveSession() {
        int filmId = filmArrayAdapter.getItem(filmSpinner.getSelectedItemPosition()).getFilm_id();
        int chem1Id = chemArrayAdapter1.getItem(chem1Spinner.getSelectedItemPosition()).getChemId();
        int chem2Id = chemArrayAdapter2.getItem(chem2Spinner.getSelectedItemPosition()).getChemId();
        int chem3Id = chemArrayAdapter3.getItem(chem3Spinner.getSelectedItemPosition()).getChemId();
        int recipeId = db.addNewRecipe(filmId, chem1Id, chem2Id, chem3Id);
        int note_id = db.insertNewNote(sessionNote);

        db.addNewSession(recipeId, note_id);
    }
}

