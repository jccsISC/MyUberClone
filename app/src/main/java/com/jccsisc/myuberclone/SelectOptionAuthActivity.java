package com.jccsisc.myuberclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectOptionAuthActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegistrar, btnYaTengoCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        btnRegistrar = findViewById(R.id.btnRegistrarClient);

        btnRegistrar.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegistrarClient:
                Intent intent = new Intent(getApplicationContext(), RegisterClientActivity.class);
                startActivity(intent);
                break;
        }
    }
}