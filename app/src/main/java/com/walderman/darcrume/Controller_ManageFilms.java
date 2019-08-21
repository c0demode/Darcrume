package com.walderman.darcrume;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Controller_ManageFilms extends AppCompatActivity {
    //on create, call a method from DatabaseHelper that fills an ArrayList<Film> and thus populates the recyclerview in activity_manage_films.xml
    DatabaseHelper db;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_films);

        db = new DatabaseHelper(getApplicationContext());

        ArrayList<Film> filmList = db.getAllFilms();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RVAdapter(filmList);


    }

}
