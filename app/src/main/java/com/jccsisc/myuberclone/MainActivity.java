package com.jccsisc.myuberclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnDriver, btnClient;
    SharedPreferences mPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        editor = mPref.edit();

        btnDriver = findViewById(R.id.btnAmDriver);
        btnClient = findViewById(R.id.btnAmClient);

        btnDriver.setOnClickListener(this);
        btnClient.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAmClient:
                editor.putString("user", "client");
                editor.apply();
                goToSelectOption();
                break;

            case R.id.btnAmDriver:
                editor.putString("user", "driver");
                editor.apply();
                goToSelectOption();
                break;
        }
    }

    private void goToSelectOption() {
        Intent intent = new Intent(getApplicationContext(), SelectOptionAuthActivity.class);
        startActivity(intent);
    }
}