package com.jccsisc.myuberclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnDriver, btnClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDriver = findViewById(R.id.btnDriver);
        btnClient = findViewById(R.id.btnClient);

        btnDriver.setOnClickListener(this);
        btnClient.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClient:
                Intent intent = new Intent(getApplicationContext(), SelectOptionAuthActivity.class);
                startActivity(intent);
            break;
        }
    }
}