package com.walderman.darcrume;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Controller_ManageFilms extends AppCompatActivity {
    //on create, call a method from DatabaseHelper that fills an ArrayList<Film> and thus populates the recyclerview in activity_manage_films.xml
    DatabaseHelper db;
    private EditText editText_Mfr;
    private EditText editText_Name;
    private RadioButton radioExp24;
    private RadioButton radioExp36;
    private RadioButton radioBW;
    private RadioButton radioColor;
    private Spinner spinnerISO;
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
        editText_Mfr = findViewById(R.id.editTextFilmsMfr);
        editText_Name = findViewById(R.id.editTextFilmsName);

        radioExp24 = findViewById(R.id.radioExp24);
        radioExp36 = findViewById(R.id.radioExp36);

        radioBW = findViewById(R.id.radioTypeBW);
        radioColor = findViewById(R.id.radioTypeColor);

        spinnerISO = findViewById(R.id.spinnerISOs);
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
    private void getSelectedFilmInfo(int position) {
        Film selectedFilm = filmList.get(position);

        editText_Mfr.setText(selectedFilm.getBrand());
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
    }

    private void createFilmList() {
        //these first 2 methods should be used sparingly
        db.truncateFilmsTable();
        addSampleFilm();
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
