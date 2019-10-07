package com.walderman.darcrume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * TODO
 * add ability to Add / Save / Delete / Copy
 * How easy to add circle "+" symbol for Add?
 */

public class ManageFilmsActivity extends AppCompatActivity {
    //on create, call a method from DatabaseHelper that fills an ArrayList<Film> and thus populates the recyclerview in activity_manage_films.xml
    private DatabaseHelper db;
    private Film selectedFilm;
    private CheckBox cbBW;
    private CheckBox cbCol;
    private Button btnEdit;
    private Button btnAdd;

    private RecyclerView recyclerView;
    private RecyclerViewFilmAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Film> filmList;
    private ArrayList<Film> filteredFilmList = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_films);
        configureVariables();
        configureListeners();
        setFilmList();
        buildRecyclerView();
    }

    private void configureListeners() {
        cbBW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterFilmList(filmList);
                updateAdapter();
            }
        });

        cbCol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterFilmList(filmList);
                updateAdapter();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Has selectedFilm been assigned a proper film from the list, or is it the empty object?
                if (selectedFilm.getFilm_id() != -1) {
                    openEditAddFilmActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "Please select a film from the list", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFilm = new Film();
                openEditAddFilmActivity();
            }
        });

    }

    private void configureVariables() {
        db = new DatabaseHelper(getApplicationContext());
        selectedFilm = new Film();
        cbBW = findViewById(R.id.cbManageFilms_BW);
        cbBW.setChecked(true);
        cbCol = findViewById(R.id.cbManageFilms_Color);
        cbCol.setChecked(true);
        btnEdit = findViewById(R.id.btnManageFilms_EditFilm);
        btnAdd = findViewById(R.id.btnManageFilms_AddFilm);
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerViewFilmAdapter(filterFilmList(filmList));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewFilmAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectedFilm = filteredFilmList.get(position);
                Toast.makeText(getApplicationContext(), selectedFilm.getBrand().toString() + " " + selectedFilm.getName().toString() + " selected.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFilmList() {
        filmList = db.getAllFilms();
    }

    private void openEditAddFilmActivity() {
        Intent intent = new Intent(this, EditAddFilm.class);
        intent.putExtra("Film Object", selectedFilm);
        startActivity(intent);
    }

    private ArrayList<Film> filterFilmList(ArrayList<Film> filmListToFilter) {
        if (filteredFilmList.size() > 0) {
            filteredFilmList.clear();
        }

        //Look at every film in the filmListToFilter. If it matches one of the checkbox types, add it to the filteredFilmList.
        for (Film film : filmListToFilter) {
            if (cbBW.isChecked() && film.getType().equals("BW")) {
                filteredFilmList.add(film);
            }
            if (cbCol.isChecked() && film.getType().equals("COLOR")) {
                filteredFilmList.add(film);
            }
        }
        return filteredFilmList;
    }

    private void updateAdapter() {
        adapter.notifyDataSetChanged();
    }
}