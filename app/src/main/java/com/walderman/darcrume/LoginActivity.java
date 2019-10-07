package com.walderman.darcrume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText editTextUsername;
    EditText editTextPassword;
    Button btnLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(getApplicationContext());

        configureVariables();
        setOnClickListeners();

        //make sure there's a user to login with. If not, call resetTables() which will insert the default user
        if (db.getUsersCount() <= 0) {
            db.resetTables();
        }

    }
    private void configureVariables() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void setOnClickListeners(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLogin();
            }
        });
    }

    private void validateLogin() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        if (db.validateLogin(username, password)){
            openMainActivity();
        }else{
            Toast.makeText(this,"Incorrect username or password",Toast.LENGTH_SHORT).show();
        }
    }

    private void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
