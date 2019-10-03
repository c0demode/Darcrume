package com.walderman.darcrume;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * TODO
 * add ability to Add / Save / Delete / Copy
 * How easy to add circle "+" symbol for Add?
 */

public class Controller_ManageFilmsActivity extends AppCompatActivity {
    //on create, call a method from DatabaseHelper that fills an ArrayList<Film> and thus populates the recyclerview in activity_manage_films.xml
    private DatabaseHelper db;
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
    private Button btnClear;
    private Button btnAdd;
    private Guideline guideline_horz1;

    private RecyclerView recyclerView;
    private RecyclerViewFilmAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Film> filmList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_films);

        db = new DatabaseHelper(getApplicationContext());

        configureVariables();
        buildFilmList();
        buildRecyclerView();
        //addSampleFilm();


    }

    private void configureVariables() {
        guideline_horz1 = findViewById(R.id.guideline_horz1);
        editText_Brand = findViewById(R.id.editTextFilmsMfr);
        editText_Name = findViewById(R.id.editTextFilmsName);

        radioGroup_exp = findViewById(R.id.radioGroupExp);
        radioExp24 = findViewById(R.id.radioExp24);
        radioExp36 = findViewById(R.id.radioExp36);

        radioGroup_type = findViewById(R.id.radioGroupType);
        radioBW = findViewById(R.id.radioTypeBW);
        radioColor = findViewById(R.id.radioTypeColor);

        spinnerISO = findViewById(R.id.spinnerISOs);

        btnSave = findViewById(R.id.btnFilmsSave);
        btnClear = findViewById(R.id.btnFilmsClear);
        btnAdd = findViewById(R.id.btnFilmsAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFilm();
            }
        });
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerViewFilmAdapter(filmList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerViewFilmAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //create a method and call it here
                //that brings up more detail and option to edit item
                getSelectedFilmInfo(position);
            }
        });
    }

    /**
     * Populate editable fields with data from Film object clicked by user
     *
     * @param position
     */
    private void getSelectedFilmInfo(final int position) {
        final Film selectedFilm = filmList.get(position);

        editText_Brand.setText(selectedFilm.getBrand());
        editText_Name.setText(selectedFilm.getName());

        if (selectedFilm.getType().equals("BW")) {
            radioBW.setChecked(true);
        } else {
            radioColor.setChecked(true);
        }

        if (selectedFilm.getExp() == 24) {
            radioExp24.setChecked(true);
        } else {
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChangesToFilm(position);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
            }
        });
    }

    /**
     * Takes the index position of the film selected from filmList
     * Ensures all fields have valid data before updating db with changes.
     * @param position
     */
    private void saveChangesToFilm(int position) {
        if(validateAllFields()) {
            Film film = filmList.get(position);
            film.setName(editText_Name.getText().toString());
            film.setBrand(editText_Brand.getText().toString());
            film.setIso(Integer.parseInt(spinnerISO.getSelectedItem().toString()));
            film.setExp(getRadioExpValue());
            film.setType(getRadioTypeValue());
            Film updatedFilm = db.updateFilm(film);
            filmList.set(position, updatedFilm);
            refreshRecyclerViewItem(position);
        }else{
            Toast.makeText(this, "Cannot Save. Incomplete Data", Toast.LENGTH_SHORT).show();
        }
    }

    private void addFilm(){
        if(validateAllFields()) {
            Film film = new Film();
            film.setBrand(editText_Brand.getText().toString().trim());
            film.setName(editText_Name.getText().toString().trim());
            film.setIso(Integer.parseInt(spinnerISO.getSelectedItem().toString()));
            film.setExp(getRadioExpValue());
            film.setType(getRadioTypeValue());

            db.insertNewFilm(film);

            //Since a new film has been added, our filmList is outdated and must be rebuilt
            buildFilmList();

            //The recyclerView must be refreshed to display the new filmList
            buildRecyclerView();
        }else{
            Toast.makeText(this, "Incomplete data", Toast.LENGTH_SHORT);
        }
    }

    /**
     * clear editText fields
     * set ISO spinner to default value
     * set all radioButtons to unchecked
     */
    private void clearFields(){
        editText_Brand.setText("");
        editText_Name.setText("");
        spinnerISO.setSelection(0);
        radioBW.setChecked(false);
        radioColor.setChecked(false);
        radioExp24.setChecked(false);
        radioExp36.setChecked(false);
    }

    private void refreshRecyclerViewItem(int position) {
        adapter.notifyItemChanged(position);
    }

    private void updateRecyclerView(){
        filmList = db.getAllFilms();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
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

    private void buildFilmList() {
        filmList = db.getAllFilms();
    }

    /**
     * Call this method before allowing user to Save film.
     * Checks fields that could be left empty or un-checked and returns 'false' if invalid data exists.
     * @return
     */
    private Boolean validateAllFields(){
        Boolean valid = true;

        if(editText_Brand.getText().toString().trim().length() < 1 ) valid = false;
        if(editText_Name.getText().toString().trim().length() < 1) valid = false;
        if(radioBW.isChecked() == false && radioColor.isChecked() == false) valid = false;
        if(radioExp24.isChecked() == false && radioExp36.isChecked() == false) valid = false;
        return valid;
    }
}