package com.jccsisc.myuberclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jccsisc.myuberclone.includes.MyToolbar;

public class SelectOptionAuthActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGoToRegister, btnGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        MyToolbar.showToolbar(SelectOptionAuthActivity.this, "Seleccione una Opción", true);

        btnGoToRegister = findViewById(R.id.btnGoToRegister);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);

        btnGoToRegister.setOnClickListener(this);
        btnGoToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoToLogin:
                goToLogin();
                break;

            case R.id.btnGoToRegister:
               goToRegister();
                break;
        }
    }

    private void goToRegister() {
        Intent intent = new Intent(getApplicationContext(), RegisterClientActivity.class);
        startActivity(intent);
    }

    private void goToLogin() {
        Intent intent1 = new Intent(SelectOptionAuthActivity.this, LoginActivity.class);
        startActivity(intent1);
    }
}