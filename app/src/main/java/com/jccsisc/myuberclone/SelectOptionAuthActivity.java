package com.jccsisc.myuberclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectOptionAuthActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegistrar, btnYaTengoCuenta;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        //paa que el toolbar funcione tenemos que irnos al manifest y ponerle una propiedad parentActivityName a la actividad
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Selecciona una Opción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRegistrar = findViewById(R.id.btnRegistrarClient);
        btnYaTengoCuenta = findViewById(R.id.btnYaCuenta);

        btnRegistrar.setOnClickListener(this);
        btnYaTengoCuenta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegistrarClient:
                Intent intent = new Intent(getApplicationContext(), RegisterClientActivity.class);
                startActivity(intent);
                break;
            case R.id.btnYaCuenta:
                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }
}