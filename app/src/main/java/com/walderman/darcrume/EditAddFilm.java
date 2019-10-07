package com.walderman.darcrume;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class EditAddFilm extends AppCompatActivity {
    private DatabaseHelper db;
    private Film film = new Film();
    private EditText editText_Brand;
    private EditText editText_Name;
    private RadioGroup radioGroup_exp;
    private RadioButton radioExp24;
    private RadioButton radioExp36;
    private RadioGroup radioGroup_type;
    private RadioButton radioBW;
    private RadioButton radioColor;
    private Spinner spinnerISO;
    private Button btnSave;
    private Button btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_film);

        configureVariables();
        configureListeners();
        if (film.getFilm_id() != -1)
            getSelectedFilmInfo(film);
    }

    private void configureListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFilm();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToManageFilmsActivity();
            }
        });
    }

    private void configureVariables() {
        db = new DatabaseHelper(getApplicationContext());
        Intent intent = getIntent();
        film = intent.getParcelableExtra("Film Object");
        editText_Brand = findViewById(R.id.etEAF_Brand);
        editText_Name = findViewById(R.id.etEAF_Name);

        radioGroup_exp = findViewById(R.id.rgEAF_Exp);
        radioExp24 = findViewById(R.id.rbEAF_24exp);
        radioExp36 = findViewById(R.id.rbEAF_36exp);

        radioGroup_type = findViewById(R.id.rgEAF_BwCol);
        radioBW = findViewById(R.id.rbEAF_BW);
        radioColor = findViewById(R.id.rbEAF_Col);

        spinnerISO = findViewById(R.id.spinnerEAF_ISO);

        btnSave = findViewById(R.id.btnEAF_Save);
        btnCancel = findViewById(R.id.btnEAF_Cancel);

    }

    private void saveFilm() {
        if(validateAllFields()) {
            film.setName(editText_Name.getText().toString());
            film.setBrand(editText_Brand.getText().toString());
            film.setIso(Integer.parseInt(spinnerISO.getSelectedItem().toString()));
            film.setExp(getRadioExpValue());
            film.setType(getRadioTypeValue());

            //If this is a new film, its id will be -1. In that case, call db.insertNewFilm
            //If this is an existing film, it will have a proper filmId. Call db.updateFilm
            if (film.getFilm_id() != -1) {
                db.updateFilm(film);
                finish();
            } else if (film.getFilm_id() == -1) {
                db.insertNewFilm(film);
                finish();
            }
        } else {
            Toast.makeText(this, "Cannot Save. Incomplete Data", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToManageFilmsActivity() {
        Intent intent2 = new Intent(this, ManageFilmsActivity.class);
        startActivity(intent2);
    }

    private void cancel() {
        //Dialog dialog = new Dialog()
    }

    private Boolean validateAllFields(){
        if(editText_Brand.getText().toString().trim().length() < 1 ) return false;
        if(editText_Name.getText().toString().trim().length() < 1) return false;
        if(radioBW.isChecked() == false && radioColor.isChecked() == false) return false;
        if(radioExp24.isChecked() == false && radioExp36.isChecked() == false) return false;
        return true;
    }

    private int getRadioExpValue(){
        if (radioExp24.isChecked()){
            return 24;
        }else{
            return 36;
        }
    }

    private String getRadioTypeValue(){
        if (radioBW.isChecked()){
            return "BW";
        }else{
            return "Color";
        }
    }

    private void getSelectedFilmInfo(Film film) {
        Film selectedFilm = film;

        editText_Brand.setText(selectedFilm.getBrand());
        editText_Name.setText(selectedFilm.getName());

        if (selectedFilm.getType().equals("BW")) {
            radioBW.setChecked(true);
        } else if (selectedFilm.getType().equals("COLOR")){
            radioColor.setChecked(true);
        }

        if (selectedFilm.getExp() == 24) {
            radioExp24.setChecked(true);
        } else if (selectedFilm.getExp() == 36){
            radioExp36.setChecked(true);
        }

        switch (selectedFilm.getIso()) {
            case 25:
                spinnerISO.setSelection(0);
                break;
            case 50:
                spinnerISO.setSelection(1);
                break;
            case 100:
                spinnerISO.setSelection(2);
                break;
            case 125:
                spinnerISO.setSelection(3);
                break;
            case 200:
                spinnerISO.setSelection(4);
                break;
            case 400:
                spinnerISO.setSelection(5);
                break;
            case 800:
                spinnerISO.setSelection(6);
                break;
            case 1600:
                spinnerISO.setSelection(7);
                break;
        }
    }
}
