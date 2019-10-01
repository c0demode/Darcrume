package com.walderman.darcrume;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Controller_MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    private Button btnManageFilms;
    private Button btnManageChems;
    private Button btnNotes;
    private Button btnDevelop;
    private Button btnNuke;
    private TextView debugtextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureVariables();
        setOnClickListeners();
    }

    private void configureVariables() {
        db = new DatabaseHelper(this);
        btnManageFilms = findViewById(R.id.btnManageFilms);
        btnManageChems = findViewById(R.id.btnChems);
        btnNotes = findViewById(R.id.btnNote);
        btnDevelop = findViewById(R.id.btnDevelop);
        btnNuke = findViewById(R.id.btnNuke);
    }

    private void setOnClickListeners() {
        btnManageFilms.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                moveToManageFilms();
            }
        });
        btnManageChems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToManageChems();
            }
        });
        btnNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNotes();
            }
        });
        btnDevelop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToDevelop();
            }
        });
        btnNuke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetDbToDefault();
            }
        });
    }

    private void moveToNotes() {
        Intent intent = new Intent(Controller_MainActivity.this, Controller_Notes.class);
        startActivity(intent);
    }

    private void moveToManageChems() {
        Intent intent = new Intent(Controller_MainActivity.this, Controller_ManageChemsActivity.class);
        startActivity(intent);
    }

    private void moveToDevelop() {
        Intent intent = new Intent(Controller_MainActivity.this, Controller_DevelopActivity.class);
        startActivity(intent);
    }

    private void moveToManageFilms() {
        Intent intent = new Intent(Controller_MainActivity.this, Controller_ManageFilmsActivity.class);
        startActivity(intent);
    }

    private void resetDbToDefault(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        db.resetTables();
    }

}
