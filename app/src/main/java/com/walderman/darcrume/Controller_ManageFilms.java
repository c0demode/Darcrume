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

public class Controller_ManageFilms extends AppCompatActivity {
    //on create, call a method from DatabaseHelper that fills an ArrayList<Film> and thus populates the recyclerview in activity_manage_films.xml
    DatabaseHelper db;
    private EditText editText_Brand;
    private EditText editText_Name;
    private RadioGroup radioGroup_exp;
    private RadioButton radioExp24;
    private RadioButton radioExp36;
    private RadioGroup radioGroup_type;
    private RadioButton radioBW;
    private RadioButton radioColor;
    private Spinner spinnerISO;
    private Button button_save;
    private Button button_add;
    private Guideline guideline_horz1;

    private RecyclerView recyclerView;
    private RVAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Film> filmList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_films);

        db = new DatabaseHelper(getApplicationContext());

        findViewsForVariables();
        createFilmList();
        buildRecyclerView();



    }

    private void findViewsForVariables() {
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

        button_save = findViewById(R.id.btn_save);
        button_add = findViewById(R.id.btn_add_new);
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RVAdapter(filmList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
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

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFilm(position);
            }
        });
    }

    private void saveFilm(int position) {
        Film film = filmList.get(position);
        film.setName(editText_Name.getText().toString());
        film.setBrand(editText_Brand.getText().toString());
        film.setIso(Integer.parseInt(spinnerISO.getSelectedItem().toString()));
        film.setExp(getRadioExpValue());
        film.setType(getRadioTypeValue());
        Film updatedFilm = db.updateFilm(film);
        filmList.set(position, updatedFilm);
        refreshRecyclerView(position);
    }

    private void refreshRecyclerView(int position) {
        adapter.notifyItemChanged(position);
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

    private void createFilmList() {
        //these first 2 methods should be used sparingly
        //db.truncateFilmsTable();
        //addSampleFilm();
        filmList = db.getAllFilms();

    }

    private void addSampleFilm() {
        //this section should be removed. using to populate w/ several films for testing purposes
        db.insertNewFilm(R.drawable.color, "Kodak", "Ektar", "Color", 100, 36);
        db.insertNewFilm(R.drawable.color, "Kodak", "Portra", "Color", 400, 36);
        db.insertNewFilm(R.drawable.color, "Fujifilm", "Fujicolor Superia", "Color", 1600, 24);
        db.insertNewFilm(R.drawable.color, "Kodak", "Gold", "Color", 200, 36);
        db.insertNewFilm(R.drawable.color, "Agfa", "Vista", "Color", 400, 36);
        db.insertNewFilm(R.drawable.bw, "Ilford", "HP5 Plus", "BW", 400, 36);
        db.insertNewFilm(R.drawable.bw, "Ilford", "Delta", "BW", 3200, 36);
        db.insertNewFilm(R.drawable.bw, "Kodak", "TMAX", "BW", 800, 36);
        db.insertNewFilm(R.drawable.bw, "Fuji", "Neopan ACROS", "BW", 100, 36);
        db.insertNewFilm(R.drawable.bw, "Ilford", "Delta", "BW", 400, 36);
    }

}
