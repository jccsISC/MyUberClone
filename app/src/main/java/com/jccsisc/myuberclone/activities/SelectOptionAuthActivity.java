package com.jccsisc.myuberclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.jccsisc.myuberclone.R;
import com.jccsisc.myuberclone.activities.client.RegisterClientActivity;
import com.jccsisc.myuberclone.activities.driver.RegisterDriverActivity;
import com.jccsisc.myuberclone.includes.MyToolbar;

public class SelectOptionAuthActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGoToRegister, btnGoToLogin;

    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        MyToolbar.showToolbar(SelectOptionAuthActivity.this, "Seleccione una Opción", true);

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

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

        String selectedUser = mPref.getString("user", ""); //obtenemos del prefer el valor que seleccionO

        if (selectedUser.equals("driver")) {
            Intent intent1 = new Intent(getApplicationContext(), RegisterDriverActivity.class);
            startActivity(intent1);
        } else if (selectedUser.equals("client")) {
            Intent intent = new Intent(getApplicationContext(), RegisterClientActivity.class);
            startActivity(intent);
        }
    }

    private void goToLogin() {
        Intent intent1 = new Intent(SelectOptionAuthActivity.this, LoginActivity.class);
        startActivity(intent1);
    }
}