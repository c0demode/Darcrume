package com.walderman.darcrume;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        TextView tvReportDate1 = findViewById(R.id.tvReportDate1);
        TextView tvReportDate2 = findViewById(R.id.tvReportDate2);
        TextView tvReportDate3 = findViewById(R.id.tvReportDate3);
        TextView tvReportFilm1 = findViewById(R.id.tvReportFilm1);
        TextView tvReportFilm2 = findViewById(R.id.tvReportFilm2);
        TextView tvReportFilm3 = findViewById(R.id.tvReportFilm3);
        ArrayList<String> report;
        report = db.retrieveReport();
        tvReportDate1.setText("Date: " +report.get(1));
        tvReportFilm1.setText("Film: " +report.get(2));
        tvReportDate2.setText("Date: " +report.get(3));
        tvReportFilm2.setText("Film: " +report.get(4));
        tvReportDate3.setText("Date: " +report.get(5));
        tvReportFilm3.setText("Film: " +report.get(6));
    }
}
