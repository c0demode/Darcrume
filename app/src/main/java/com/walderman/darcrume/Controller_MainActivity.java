package com.walderman.darcrume;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Controller_MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    private Button btnManageFilms;
    private Button btnDevelop;
    private TextView debugtextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        btnManageFilms = findViewById(R.id.btnManageFilms);
        btnDevelop = findViewById(R.id.btnDevelop);
        debugtextview = findViewById(R.id.pickledebug);

        btnManageFilms.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                moveToManageFilms();
            }
        });

        btnDevelop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToDevelop();
            }
        });
    }

    private void moveToDevelop() {
        Intent intent = new Intent(Controller_MainActivity.this, Controller_Develop.class);
        startActivity(intent);
    }

    private void moveToManageFilms() {
        Intent intent = new Intent(Controller_MainActivity.this, Controller_ManageFilms.class);
        startActivity(intent);
    }


}
