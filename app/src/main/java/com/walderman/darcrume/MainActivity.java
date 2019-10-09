package com.walderman.darcrume;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    private Button btnHowTo;
    private Button btnManageFilms;
    private Button btnManageChems;
    private Button btnNotes;
    private Button btnDevelop;
    private Button btnNuke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureVariables();
        setOnClickListeners();
    }

    private void configureVariables() {
        db = new DatabaseHelper(this);
        btnHowTo = findViewById(R.id.btnHowToActivity);
        btnManageFilms = findViewById(R.id.btnManageFilms);
        btnManageChems = findViewById(R.id.btnChems);
        btnNotes = findViewById(R.id.btnNote);
        btnDevelop = findViewById(R.id.btnDevelop);
        btnNuke = findViewById(R.id.btnNuke);
    }

    private void setOnClickListeners() {
        btnHowTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToHowTo();
            }
        });
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

    private void moveToHowTo() {
        Intent intent = new Intent(MainActivity.this, HowTo.class);
        startActivity(intent);
    }

    private void moveToNotes() {
        Intent intent = new Intent(MainActivity.this, Notes.class);
        startActivity(intent);
    }

    private void moveToManageChems() {
        Intent intent = new Intent(MainActivity.this, ManageChemsActivity.class);
        startActivity(intent);
    }

    private void moveToDevelop() {
        Intent intent = new Intent(MainActivity.this, DevelopActivity.class);
        startActivity(intent);
    }

    private void moveToManageFilms() {
        Intent intent = new Intent(MainActivity.this, ManageFilmsActivity.class);
        startActivity(intent);
    }

    private void resetDbToDefault(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        db.resetTables();
    }

}
