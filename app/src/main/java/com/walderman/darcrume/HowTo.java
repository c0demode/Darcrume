package com.walderman.darcrume;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class HowTo extends AppCompatActivity {
 private RadioButton rbBW;
 private RadioButton rbCol;
 private TextView tvInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto);
        rbBW = findViewById(R.id.rbHowToBW);
        rbCol = findViewById(R.id.rbHowToColor);
        tvInstructions = findViewById(R.id.tvInstructions);

        rbBW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvInstructions.setText(getString(R.string.howToBW));
            }
        });

        rbCol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvInstructions.setText(getString(R.string.howToColor));
            }
        });
    }
}
