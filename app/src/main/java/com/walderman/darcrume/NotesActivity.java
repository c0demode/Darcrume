package com.walderman.darcrume;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {
    DatabaseHelper db;
    private RecyclerView recyclerView;
    private RecyclerViewNoteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnNoteSearch;
    private EditText etNoteSearch;
    private ArrayList<Note> noteList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        configureVariables();
        buildRecyclerView();
    }

    private void configureVariables() {
        db = new DatabaseHelper(getApplicationContext());
        noteList = db.getAllNotes();
        btnNoteSearch = findViewById(R.id.btnNoteSearch);
        etNoteSearch = findViewById(R.id.etNote_Search);

        btnNoteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchNotes();
            }
        });
    }

    private void searchNotes() {
        String search = etNoteSearch.getText().toString();
        noteList = db.searchNotes(search);
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.rvNotes);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerViewNoteAdapter(noteList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
