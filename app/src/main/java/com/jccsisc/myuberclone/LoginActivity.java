package com.jccsisc.myuberclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText tieGmail, tiePassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tieGmail = findViewById(R.id.tieEmail);
        tiePassword = findViewById(R.id.tiePassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {
        String email = tieGmail.getText().toString();
        String password = tiePassword.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            if (password.length()>=6) {
                Toast.makeText(getApplicationContext(), "Logeado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}