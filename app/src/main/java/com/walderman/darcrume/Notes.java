package com.walderman.darcrume;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Notes extends AppCompatActivity {
    DatabaseHelper db;
    EditText editTextNotes;
    Button btnSaveNotes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        db = new DatabaseHelper(getApplicationContext());

        configureVariables();
        setOnClickListeners();
    }

    private void configureVariables() {
        editTextNotes = findViewById(R.id.editTextNotes);
        btnSaveNotes = findViewById(R.id.btnSaveNote);
    }

    private void setOnClickListeners() {
        btnSaveNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertNote();
            }
        });
    }

    private void insertNote(){
        if (db.insertNewNote(editTextNotes.getText().toString())){
            Toast.makeText(this,"Note Saved!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Error - unable to save note.",Toast.LENGTH_SHORT).show();
        }
    }

}
