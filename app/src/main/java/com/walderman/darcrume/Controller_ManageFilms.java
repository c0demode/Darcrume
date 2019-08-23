package com.walderman.darcrume;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Controller_ManageFilms extends AppCompatActivity {
    //on create, call a method from DatabaseHelper that fills an ArrayList<Film> and thus populates the recyclerview in activity_manage_films.xml
    DatabaseHelper db;
    private RecyclerView recyclerView;
    private RVAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView debugText;
    private ArrayList<Film> filmList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_films);

        db = new DatabaseHelper(getApplicationContext());

        createFilmList();
        buildRecyclerView();




        debugText = findViewById(R.id.debugText);
        debugText.setText("Number of films in ArrayList I'm trying to use: " + filmList.size() +"\nFirst item name: " + filmList.get(0).getBrand() + " " + filmList.get(0).getName());

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
                debugText.setText("item clicked at position: "+position);
            }
        });
    }

    private void createFilmList() {
        filmList = db.getAllFilms();
        //this section should be removed. using to populate w/ several films for testing purposes
//        db.insertNewFilm(R.drawable.bw,"Ilford","HP5+","BW",400,36);
//        db.insertNewFilm(R.drawable.color,"Kodak","Portra","Color",400,36);
//        db.insertNewFilm(R.drawable.bw,"Ilford","Pan-F","BW",160,24);
//        db.insertNewFilm(R.drawable.color,"FujiFilm","Velvia","Color",400,36);
//        db.insertNewFilm(R.drawable.bw,"Kodak","T-Max","BW",400,36);
//        db.insertNewFilm(R.drawable.color,"FilmZone","Lazer Crease","Color",400,36);
    }


}
