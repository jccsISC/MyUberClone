package com.jccsisc.myuberclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.jccsisc.myuberclone.R;
import com.jccsisc.myuberclone.activities.client.MapClientActivity;
import com.jccsisc.myuberclone.activities.driver.MapDriveActivity;

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

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String user = mPref.getString("user", "");
            if (user.equals("driver")) {
                Intent intent1 = new Intent(getApplicationContext(), MapDriveActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //Con esto ya no podrá regresar al activity anterior
                startActivity(intent1);
            } else if (user.equals("client")) {
                Intent intent = new Intent(getApplicationContext(), MapClientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //Con esto ya no podrá regresar al activity anterior
                startActivity(intent);
            }
        }
    }
}